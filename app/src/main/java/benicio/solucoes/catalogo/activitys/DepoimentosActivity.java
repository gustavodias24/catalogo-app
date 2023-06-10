package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import benicio.solucoes.catalogo.adapeters.AdapterDepoimentos;
import benicio.solucoes.catalogo.databinding.ActivityDepoimentosBinding;
import benicio.solucoes.catalogo.databinding.ActivityMenuBinding;

public class DepoimentosActivity extends AppCompatActivity {

    private ActivityDepoimentosBinding vb;
    private List<String> lista_videos = new ArrayList<>();
    private RecyclerView recyclerVideos;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vb = ActivityDepoimentosBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());

        getSupportActionBar().setTitle("Depoimento de clientes");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista_videos.add("http://www.youtube.com/embed/" + "Xw4izW-AP6E" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "z8jbufLbovc" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "cibIswOBwNc" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "or-ekxvHqMU" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "8Q0jF3WmR6E" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "Ug7gQVhHCkw" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "EhuS9aau4Es" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "1VYozbJrowI" + "?autoplay=0&vq=small");
        lista_videos.add("http://www.youtube.com/embed/" + "K1XJB3cB1Ig" + "?autoplay=0&vq=small");

        recyclerVideos = vb.RecyclerDepoimentos;
        recyclerVideos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerVideos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setAdapter(new AdapterDepoimentos(lista_videos, getApplicationContext()));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}