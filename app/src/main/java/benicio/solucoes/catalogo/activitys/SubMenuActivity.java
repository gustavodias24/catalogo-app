package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

//        vb.btnComents.setBackgroundColor(Color.parseColor("#00FF00"));
//        vb.btnLikes.setBackgroundColor(Color.parseColor("#00FF00"));
//        vb.btnSeguidores.setBackgroundColor(Color.parseColor("#00FF00"));
//        vb.btnViews.setBackgroundColor(Color.parseColor("#00FF00"));


        vb.btnComents.setText("Comentário para o YouTube");
        vb.btnLikes.setText("Likes para o YouTube");
        vb.btnSeguidores.setText("Inscritos para o YouTube");
        vb.btnViews.setText("Engajamento para o YouTube");
    }

    public void configurarInsta(){

        vb.layoutSubMenu.setBackgroundResource(R.drawable.background_repeat_insta);

        vb.btnComents.setText("Comentário para Instagram");
        vb.btnLikes.setText("Likes para Instagram");
        vb.btnSeguidores.setText("Seguidores para Instagram");
        vb.btnViews.setText("Visualizações para Instagram");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}