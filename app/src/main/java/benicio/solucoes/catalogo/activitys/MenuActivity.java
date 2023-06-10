package benicio.solucoes.catalogo.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import benicio.solucoes.catalogo.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding vb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vb = ActivityMenuBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());

        getSupportActionBar().setTitle(
                "Menu principal"
        );

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        vb.btnYt.setOnClickListener(viewYT ->{
            Intent i = new Intent(this, SubMenuActivity.class);
            i.putExtra("intType", 0);
            i.putExtra("titleBar", "Catálogo do YouTube");
            startActivity(i);
        });

        vb.btnInsta.setOnClickListener( viewInsta -> {
            Intent i = new Intent(this, SubMenuActivity.class);
            i.putExtra("intType", 1);
            i.putExtra("titleBar", "Catálogo do Instagram");
            startActivity(i);
        });

        vb.btnDepoimentos.setOnClickListener( viewCompt -> {
            startActivity(new Intent(getApplicationContext(), DepoimentosActivity.class));
        });

        vb.btnCompartilhatube.setOnClickListener( viewTube ->{
            AlertDialog.Builder b = new AlertDialog.Builder(MenuActivity.this);
            b.setTitle("Conheça o CompartilhaTube!");
            b.setMessage("Aplicativo de troca de engajamento grátis na PlayStore com mais de 10.000 usuários!");
            b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=br.com.internet.ganhatube")));
                }
            });

            b.create().show();
        });

        vb.btnOutras.setOnClickListener( viewOutras ->{
            AlertDialog.Builder b = new AlertDialog.Builder(MenuActivity.this);
            b.setTitle("Conhecer outros serviços.");
            b.setMessage("Você será redirecionado para o meu Whastapp para saber mais sobre outros serviços.");
            b.setPositiveButton("ok", (dialog, which) -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=5591984044333&text=Ol%C3%A1,%20gostaria%20de%20saber%20se%20voc%C3%AA%20tem%20servi%C3%A7os%20para%20...."))));
            b.create().show();
        });


    }
}