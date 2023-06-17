package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import benicio.solucoes.catalogo.R;
import benicio.solucoes.catalogo.databinding.LayoutEmailMercadoPagoBinding;
import benicio.solucoes.catalogo.services.Service;
import benicio.solucoes.catalogo.databinding.ActivityMainBinding;
import benicio.solucoes.catalogo.databinding.LayoutCarregamentoBinding;
import benicio.solucoes.catalogo.models.CatalogoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {



    private ActivityMainBinding vb;
    private Retrofit retrofit;
    private Service myService;

    private CatalogoModel catalogoModel;
    private Dialog dialog_carregando, dialog_pix;

    private int qtd_comprada, precoAtual;
    private String produto_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        vb = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());

        AlertDialog.Builder bc = new AlertDialog.Builder(MainActivity.this);
        bc.setCancelable(false);
        bc.setView(LayoutCarregamentoBinding.inflate(getLayoutInflater()).getRoot());
        dialog_carregando = bc.create();
        dialog_carregando.show();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();



        retrofit = new Retrofit.Builder()
                .baseUrl("https://catalogo-teal.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myService = retrofit.create(Service.class);

        myService.get_catalogo(b.getInt("type"), b.getInt("subType")).enqueue(new Callback<CatalogoModel>() {
            @Override
            public void onResponse(Call<CatalogoModel> call, Response<CatalogoModel> response) {
                if (response.isSuccessful() ){
                    catalogoModel = response.body();
                    config_catalogo();
                }else{
                    Toast.makeText(getApplicationContext(),
                            String.format("Erro %s ao fazer requisição", response.code()),
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CatalogoModel> call, Throwable t) {

            }
        });

        vb.btnPagar.setOnClickListener( viewPagar -> {
            AlertDialog.Builder dialog_builder_pix = new AlertDialog.Builder(MainActivity.this);
            dialog_builder_pix.setCancelable(false);
            dialog_builder_pix.setNegativeButton("Cancelar", (dialog, which) -> dialog_pix.dismiss());
            LayoutEmailMercadoPagoBinding vbPix = LayoutEmailMercadoPagoBinding.inflate(getLayoutInflater());
            vbPix.btnConfirmarEmail.setOnClickListener( btnConfirmarEmailView -> {
                String email_pix = vbPix.editTextEmail.getText().toString();
                String regex_email = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                Pattern pattern = Pattern.compile(regex_email);
                Matcher matcher = pattern.matcher(email_pix);

                if (matcher.matches()){
                    Intent i = new Intent(getApplicationContext(), PagamentoActivity.class);
                    i.putExtra("EmailPix", email_pix);
                    //i.putExtra("valorPix", precoAtual);
                    i.putExtra("valorPix", 1);
                    i.putExtra("nomePix", produto_title);
                    i.putExtra("qtdComprada", qtd_comprada);
                    startActivity(i);
                    Toast.makeText(this,
                            "Pague o Pix para o seu pedido ser encaminhado com sucesso.",
                            Toast.LENGTH_LONG).show();
                    dialog_pix.dismiss();
                }else{
                    Toast.makeText(this,
                            "Digite um E-mail válido!", Toast.LENGTH_SHORT).show();
                }
            });
            dialog_builder_pix.setView(vbPix.getRoot());
            dialog_pix = dialog_builder_pix.create();
            dialog_pix.show();

        });

    }

    public void config_catalogo(){
        String zapPromo =
        "https://api.whatsapp.com/send?phone=5591984044333&text=Ol%C3%A1%2C%20gostaria%20de%20saber%20as%20promo%C3%A7%C3%B5es%20de%20"+
                catalogoModel.getTitle().replace("","%20");

        vb.btnPromo.setOnClickListener( btnPromoView -> {
            startActivity(
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(zapPromo)));
        });

        if ( catalogoModel.getType() == 0) {
            vb.ly.setBackgroundResource(R.drawable.background_repat_yt);
        }else {
            vb.ly.setBackgroundResource(R.drawable.background_repeat_insta);
        }

        getSupportActionBar().setTitle(
                String.format("Preços de %s", catalogoModel.getTitle())
        );

        Glide.with(MainActivity.this).load(catalogoModel.getUrl_photo()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                vb.imagePromo.setVisibility(View.VISIBLE);
                vb.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                vb.imagePromo.setVisibility(View.VISIBLE);
                vb.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(vb.imagePromo);

        precoAtual = catalogoModel.getBase();
        qtd_comprada = catalogoModel.getMin();
        produto_title = catalogoModel.getTitle();

        vb.textView.setText(String.format("Valor total R$ %s", catalogoModel.getBase()));

        vb.editTextQtd.setText(catalogoModel.getMin() + "");
        vb.seekBar2.setMax(catalogoModel.getMax());
        vb.seekBar2.setMin(catalogoModel.getMin());

        vb.textViewMax.setText(String.format("Máx %s", catalogoModel.getMax()));
        vb.textViewMin.setText(String.format("Min %s", catalogoModel.getMin()));

        vb.textDescribeProduto.setText(catalogoModel.getDescri());
        vb.textTitleProduto.setText(catalogoModel.getTitle());

        vb.editTextQtd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int n_digitado = 0;

                if (s.length() == 0) {
                    vb.seekBar2.setProgress(0);
                    vb.editTextQtd.setText("0"); // Define o texto como "0" sem o editText for vazio
                }else{
                    n_digitado = Integer.parseInt(s.toString());
                }

                if ( n_digitado >= catalogoModel.getMin() && n_digitado  <= catalogoModel.getMax()){
                    vb.textView.setTextColor(Color.WHITE);

                    vb.seekBar2.setProgress(n_digitado);
                    int valorReal = (catalogoModel.getBase() * n_digitado ) / catalogoModel.getMin();
                    vb.textView.setText(String.format("Valor total R$ %s", valorReal));
                }else{
                    vb.textView.setTextColor(Color.RED);
                    vb.textView.setText("Esse valor é inválido");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        vb.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int valorReal = (catalogoModel.getBase() * i) / catalogoModel.getMin();
                vb.editTextQtd.setText(i + "");
                precoAtual = valorReal;
                qtd_comprada = i;
                vb.textView.setText(String.format("Valor total R$ %s", valorReal));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog_carregando.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}