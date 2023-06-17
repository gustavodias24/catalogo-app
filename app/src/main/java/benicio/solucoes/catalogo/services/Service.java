package benicio.solucoes.catalogo.services;

import benicio.solucoes.catalogo.models.CatalogoModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("{type}/{subType}")
    Call<CatalogoModel> get_catalogo(@Path("type") int type, @Path("subType") int subType);

}
