package benicio.solucoes.catalogo.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import benicio.solucoes.catalogo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        vb = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(vb.getRoot());

        getSupportActionBar().setTitle(
                String.format("Preços de %s", "Visualizações no YouTube")
        );

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                if ( n_digitado >= 500 && n_digitado  <= 15000){
                    vb.textView.setTextColor(Color.WHITE);

                    vb.seekBar2.setProgress(n_digitado);
                    int valorReal = (20 * n_digitado ) / 500;
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
                    int valorReal = (20 * i) / 500;
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}