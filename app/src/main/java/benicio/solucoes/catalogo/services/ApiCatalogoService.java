package benicio.solucoes.catalogo.services;

import benicio.solucoes.catalogo.models.CatalogoModel;
import benicio.solucoes.catalogo.models.GenericModel;
import benicio.solucoes.catalogo.models.PedidoModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCatalogoService {

    @GET("{type}/{subType}")
    Call<CatalogoModel> get_catalogo(@Path("type") int type, @Path("subType") int subType);

    @POST("/salvarPedido")
    Call<PedidoModel> save_order(@Body PedidoModel pedido);

    @POST("/verificarPagamento")
    Call<GenericModel> verify_order (@Body GenericModel msg);

}
