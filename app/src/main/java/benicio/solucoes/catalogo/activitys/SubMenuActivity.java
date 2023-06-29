package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import benicio.solucoes.catalogo.R;
import benicio.solucoes.catalogo.databinding.ActivitySubMenuBinding;
import benicio.solucoes.catalogo.databinding.LayoutCarregamentoBinding;
import benicio.solucoes.catalogo.models.GenericModel;
import benicio.solucoes.catalogo.services.ApiCatalogoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubMenuActivity extends AppCompatActivity {

    private ActivitySubMenuBinding vb;
    private Retrofit retrofit;
    private ApiCatalogoService service;

    private Dialog dialog_carregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vb = ActivitySubMenuBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());


        Bundle b = getIntent().getExtras();

        getSupportActionBar().setTitle(b.getString("titleBar"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configurarDialogCarregando();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://catalogo-teal.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiCatalogoService.class);

        service.get_code().enqueue(new Callback<GenericModel>() {
            @Override
            public void onResponse(Call<GenericModel> call, Response<GenericModel> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if ( response.body().getMsg().equals("0") ){
                        configurarDefault();
                    }else{
                        switch ( b.getInt("intType") ){
                            // Tipo YouTube
                            case 0:
                                configurarYouTube();
                                break;
                            case 1:
                                configurarInsta();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Você não pode acessar essa tela!", Toast.LENGTH_LONG).show();
                                finish();
                                break;
                        }
                    }
                }
                else{
                    Toast.makeText(SubMenuActivity.this, "Erro de conexão, tente novamente!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                dialog_carregando.dismiss();
            }

            @Override
            public void onFailure(Call<GenericModel> call, Throwable t) {

            }
        });

    }
    public void configurarDialogCarregando(){
        AlertDialog.Builder b = new AlertDialog.Builder(SubMenuActivity.this);
        b.setView(LayoutCarregamentoBinding.inflate(getLayoutInflater()).getRoot());
        b.setCancelable(false);
        dialog_carregando = b.create();
        dialog_carregando.show();
    }
    public void configurarYouTube(){

        vb.layoutSubMenu.setBackgroundResource(R.drawable.background_repat_yt);

        vb.btnComents.setText("Comentários para o YouTube");
        vb.btnLikes.setText("Likes para o YouTube");
        vb.btnSeguidores.setText("Inscritos para o YouTube");
        vb.btnViews.setText("Engajamento para o YouTube");

        vb.btnViews.setOnClickListener( viewYT -> {
            go_catalogo(0, 0);
        });

        vb.btnSeguidores.setOnClickListener( viewSubs -> {
            go_catalogo(0, 1);
        });

        vb.btnLikes.setOnClickListener( viewLikes -> {
            go_catalogo(0, 2);
        });

        vb.btnComents.setOnClickListener( viewComents -> {
            go_catalogo(0, 3);
        });
    }

    public void configurarInsta(){

        vb.layoutSubMenu.setBackgroundResource(R.drawable.background_repeat_insta);

        vb.btnComents.setText("Comentários para Instagram");
        vb.btnLikes.setText("Likes para Instagram");
        vb.btnSeguidores.setText("Seguidores para Instagram");
        vb.btnViews.setText("Visualizações para Instagram");

        vb.btnViews.setOnClickListener( viewYT -> {
            go_catalogo(1, 1);
        });

        vb.btnSeguidores.setOnClickListener( viewSubs -> {
            go_catalogo(1, 2);
        });

        vb.btnLikes.setOnClickListener( viewLikes -> {
            go_catalogo(1, 0);
        });

        vb.btnComents.setOnClickListener( viewComents -> {
            go_catalogo(1, 3);
        });
    }

    public void configurarDefault(){
        vb.btnComents.setText("Comentários");
        vb.btnLikes.setText("Likes");
        vb.btnSeguidores.setText("Seguidores");
        vb.btnViews.setText("Visualizações");

        vb.btnViews.setOnClickListener( viewYT -> {
            startActivity(new Intent(getApplicationContext(), DefaultActivity.class));
        });

        vb.btnSeguidores.setOnClickListener( viewSubs -> {
            startActivity(new Intent(getApplicationContext(), DefaultActivity.class));
        });

        vb.btnLikes.setOnClickListener( viewLikes -> {
            startActivity(new Intent(getApplicationContext(), DefaultActivity.class));
        });

        vb.btnComents.setOnClickListener( viewComents -> {
            startActivity(new Intent(getApplicationContext(), DefaultActivity.class));
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void go_catalogo(int type, int subType){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("type", type);
        i.putExtra("subType", subType);
        startActivity(i);
    }

}