package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import benicio.solucoes.catalogo.R;
import benicio.solucoes.catalogo.databinding.ActivityPagamentoBinding;
import benicio.solucoes.catalogo.databinding.LayoutCarregamentoBinding;
import benicio.solucoes.catalogo.models.GenericModel;
import benicio.solucoes.catalogo.models.MercadoPagoModel;
import benicio.solucoes.catalogo.models.PagamentoPixModel;
import benicio.solucoes.catalogo.models.PayerModel;
import benicio.solucoes.catalogo.models.PedidoModel;
import benicio.solucoes.catalogo.services.ApiCatalogoService;
import benicio.solucoes.catalogo.services.MercadoPagoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PagamentoActivity extends AppCompatActivity {

    private Retrofit retrofitMercadoPago, retrofitApiCatalogo;

    private AlertDialog dialog_carregando;

    private static final String TOKEN =
            "Bearer APP_USR-3237615834213855-061314-15538c69abdaae50fdb43565a99cfbac-782330883";
    private static final String TOKEN_TEST =
            "Bearer TEST-3237615834213855-061314-089b4e11d795e4c0347b6eca3f1c9791-782330883";

    private MercadoPagoService mercadoPagoService;
    private ApiCatalogoService apiCatalogoService;

    private ActivityPagamentoBinding vb;

    private String id_pedido;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vb = ActivityPagamentoBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());

        configurarAlertCarregando();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setTitle("Pagamento");

        retrofitMercadoPago = new Retrofit.Builder()
                .baseUrl("https://api.mercadopago.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitApiCatalogo = new Retrofit.Builder()
                .baseUrl("https://catalogo-teal.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mercadoPagoService = retrofitMercadoPago.create(MercadoPagoService.class);
        apiCatalogoService = retrofitApiCatalogo.create(ApiCatalogoService.class);

        Bundle b = getIntent().getExtras();

        mercadoPagoService.gerar_pix(
                TOKEN,
                new PagamentoPixModel(
                        b.getInt("valorPix"),
                        "pix",
                        String.format(
                                "%s de %s",
                                b.getInt("qtdComprada"),
                                b.getString("nomePix")
                        ),
                        new PayerModel(b.getString("EmailPix"))
                )
        ).enqueue(new Callback<MercadoPagoModel>() {
            @Override
            public void onResponse(Call<MercadoPagoModel> call, Response<MercadoPagoModel> response) {
                if (response.isSuccessful()){
                    MercadoPagoModel mercadoPagoModel = response.body();
                    id_pedido = mercadoPagoModel.getId(); // para verificar se o pagamento já foi concluído
                    salvarPedido(
                            mercadoPagoModel.getId(),
                            b.getString("nomePix"),
                            b.getString("link"),
                            b.getInt("valorPix"),
                            mercadoPagoModel.getStatus(),
                            mercadoPagoModel
                    );
                }else{
                    msgProblemaConexao();
                }
            }

            @Override
            public void onFailure(Call<MercadoPagoModel> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finalizar_pedido, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.finalizar_pedido){
            dialog_carregando.show();
            apiCatalogoService.verify_order(new GenericModel(id_pedido)).enqueue(new Callback<GenericModel>() {
                @Override
                public void onResponse(Call<GenericModel> call, Response<GenericModel> response) {
                    String msgVerify = "";
                    switch (response.code()){
                        case 200:
                            msgVerify = "Pedido concluído com sucesso!";

                            startActivity(
                                    new Intent(
                                            getApplicationContext(),
                                            PagamentoConcluitdoActivity.class
                                    ));

                            finish();
                            break;
                        case 400:
                            msgVerify = "Pedido com status ainda não pago!";
                            break;
                        default:
                            msgVerify = "Tente novamente!";
                            break;
                    }
                    Toast.makeText(PagamentoActivity.this, msgVerify, Toast.LENGTH_LONG).show();
                    dialog_carregando.dismiss();
                }

                @Override
                public void onFailure(Call<GenericModel> call, Throwable t) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
        
    }

    public void salvarPedido(String id, String descri, String link, int preco, String status, MercadoPagoModel mercadoPagoModel){
        apiCatalogoService.save_order(
                new PedidoModel(
                        id,
                        descri,
                        String.valueOf(preco),
                        status,
                        link
                )
        ).enqueue(new Callback<PedidoModel>() {
            @Override
            public void onResponse(Call<PedidoModel> call, Response<PedidoModel> response) {
                if (response.isSuccessful()){
                    configurarWebView(mercadoPagoModel.getPoint_of_interaction().getTransaction_data().getTicket_url());
                }else{
                    msgProblemaConexao();
                }
            }

            @Override
            public void onFailure(Call<PedidoModel> call, Throwable t) {

            }
        });
    }

    public  void msgProblemaConexao(){
        Toast.makeText(PagamentoActivity.this,
                "Você teve algum problema de conexão.",
                Toast.LENGTH_SHORT).show();
        Toast.makeText(PagamentoActivity.this,
                "Tente novamente!",
                Toast.LENGTH_LONG).show();
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void configurarWebView(String url){
        vb.webViewPagamento.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog_carregando.dismiss();
                super.onPageFinished(view, url);
            }
        });
        vb.webViewPagamento.getSettings().setJavaScriptEnabled(true);
        vb.webViewPagamento.loadUrl(url);
    }

    public void configurarAlertCarregando(){
        AlertDialog.Builder db_carregando = new AlertDialog.Builder(PagamentoActivity.this);
        db_carregando.setCancelable(false);
        db_carregando.setView(LayoutCarregamentoBinding.inflate(getLayoutInflater()).getRoot());
        dialog_carregando = db_carregando.create();
        dialog_carregando.show();
    }

}