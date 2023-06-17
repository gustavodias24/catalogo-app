package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import benicio.solucoes.catalogo.databinding.ActivityPagamentoConcluitdoBinding;

public class PagamentoConcluitdoActivity extends AppCompatActivity {

    private ActivityPagamentoConcluitdoBinding vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityPagamentoConcluitdoBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vb.btnVoltarMenu.setOnClickListener(v -> {
            voltarMenu();
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if ( item.getItemId() == android.R.id.home){
            voltarMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    public void voltarMenu(){
        startActivity(
                new Intent(getApplicationContext(), MenuActivity.class)
        );
        finish();
    }

}