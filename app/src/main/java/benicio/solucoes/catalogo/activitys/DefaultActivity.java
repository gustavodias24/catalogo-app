package benicio.solucoes.catalogo.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebViewClient;

import benicio.solucoes.catalogo.databinding.ActivityDefaultBinding;

public class DefaultActivity extends AppCompatActivity {

    private ActivityDefaultBinding vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vb = ActivityDefaultBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(vb.getRoot());

        vb.prices.setText(
                "YouTube:\n" +
                        "Visualizações no YouTube - Apartir de 100 visualizações: R$ 5\n" +
                        "Curtidas no YouTube - Apartir de 100 curtidas: R$ 10\n" +
                        "Comentários no YouTube - Apartir de 10 comentários: R$ 10\n\n" +
                        "Instagram:\n" +
                        "Seguidores no Instagram - Apartir de 100 seguidores: R$ 10\n" +
                        "Curtidas no Instagram - Apartir de 100 curtidas: R$ 5\n" +
                        "Comentários no Instagram - Apartir de 10 comentários: R$ 5"
        );
    }
}