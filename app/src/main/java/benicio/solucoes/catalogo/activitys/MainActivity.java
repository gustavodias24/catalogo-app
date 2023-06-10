package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import benicio.solucoes.catalogo.R;
import benicio.solucoes.catalogo.Service;
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

    private Dialog dialog_carregando;

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
    }

    public void config_catalogo(){
        if ( catalogoModel.getType() == 0) {
            vb.ly.setBackgroundResource(R.drawable.background_repat_yt);
        }else {
            vb.ly.setBackgroundResource(R.drawable.background_repeat_insta);
        }
        getSupportActionBar().setTitle(
                String.format("Preços de %s", catalogoModel.getTitle())
        );

        Glide.with(this).load(catalogoModel.getUrl_photo()).into(vb.imagePromo);
        vb.imagePromo.setVisibility(View.VISIBLE);
        vb.editTextQtd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int n_digitado = 0;

                if (s.length() == 0) {
                    vb.seekBar2.setProgress(0);
                    vb.editTextQtd.setText("0"); // Define o texto como "0"
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