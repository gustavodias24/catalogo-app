package benicio.solucoes.catalogo.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import benicio.solucoes.catalogo.databinding.ActivityPagamentoBinding;
import benicio.solucoes.catalogo.databinding.LayoutCarregamentoBinding;
import benicio.solucoes.catalogo.models.MercadoPagoModel;
import benicio.solucoes.catalogo.models.PagamentoPixModel;
import benicio.solucoes.catalogo.models.PayerModel;
import benicio.solucoes.catalogo.services.MercadoPagoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PagamentoActivity extends AppCompatActivity {

    private Retrofit retrofitMercadoPago;

    private AlertDialog dialog_carregando;

    private static final String TOKEN =
            "Bearer APP_USR-3237615834213855-061314-15538c69abdaae50fdb43565a99cfbac-782330883";
    private static final String TOKEN_TEST =
            "Bearer TEST-3237615834213855-061314-089b4e11d795e4c0347b6eca3f1c9791-782330883";
    private MercadoPagoService mercadoPagoService;

    private ActivityPagamentoBinding vb;

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

        mercadoPagoService = retrofitMercadoPago.create(MercadoPagoService.class);

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
                    assert mercadoPagoModel != null;
                    configurarWebView(mercadoPagoModel.getPoint_of_interaction().getTransaction_data().getTicket_url());
                }else{
                    Toast.makeText(PagamentoActivity.this,
                            "Você teve algum problema de conexão.",
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(PagamentoActivity.this,
                            "Tente novamente!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<MercadoPagoModel> call, Throwable t) {

            }
        });


//        vb.textResultTest.setText(
//                String.format(
//                        " %s / %s / R$ %s / %s",
//                        b.getString("EmailPix"),
//                        b.getString("nomePix"),
//                        b.getInt("valorPix"),
//                        b.getInt("qtdComprada")
//                )
//        );

    }


    @SuppressLint("SetJavaScriptEnabled")
    public void configurarWebView(String url){
        vb.webViewPagamento.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("MAYARA", "URL: "+request.getUrl());
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
        db_carregando.setView(LayoutCarregamentoBinding.inflate(getLayoutInflater()).getRoot());
        dialog_carregando = db_carregando.create();
        dialog_carregando.show();
    }

}