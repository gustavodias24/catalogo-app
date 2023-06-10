package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import benicio.solucoes.catalogo.R;
import benicio.solucoes.catalogo.databinding.ActivitySubMenuBinding;

public class SubMenuActivity extends AppCompatActivity {

    private ActivitySubMenuBinding vb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vb = ActivitySubMenuBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());


        Bundle b = getIntent().getExtras();

        getSupportActionBar().setTitle(b.getString("titleBar"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        switch ( b.getInt("intType") ){
            // Tipo YouTube
            case 0:
                configurarYouTube();
                break;
            case 1:
                configurarInsta();
                break;
            default:
                Toast.makeText(this, "Você não pode acessar essa tela!", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

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