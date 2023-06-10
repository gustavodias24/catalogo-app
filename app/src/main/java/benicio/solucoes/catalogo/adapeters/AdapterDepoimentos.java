package benicio.solucoes.catalogo.adapeters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benicio.solucoes.catalogo.R;
import benicio.solucoes.catalogo.databinding.LayoutDepoimentosVideosBinding;

public class AdapterDepoimentos extends RecyclerView.Adapter<AdapterDepoimentos.MyViewHolder> {

    List<String> linkVideos;
    Context c;

    public AdapterDepoimentos(List<String> linkVideos, Context c) {
        this.linkVideos = linkVideos;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_depoimentos_videos, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String linkVideo  = linkVideos.get(position);


       holder.webVideo.getSettings().setJavaScriptEnabled(true);
       holder.webVideo.setWebViewClient(new WebViewClient(){
           @Override
           public void onPageFinished(WebView view, String url) {
               holder.progress.setVisibility(View.GONE);
               super.onPageFinished(view, url);
           }
       });
       holder.webVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
       holder.webVideo.setWebChromeClient(new WebChromeClient());
       //"http://www.youtube.com/embed/" + "FJtYk3S8y5s" + "?autoplay=0&vq=small"
       holder.webVideo.loadUrl(linkVideo);
    }

    @Override
    public int getItemCount() {
        return linkVideos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private WebView webVideo;
        private ProgressBar progress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            webVideo = itemView.findViewById(R.id.webVideo);
            progress = itemView.findViewById(R.id.progressDepoiments);
        }
    }
}
