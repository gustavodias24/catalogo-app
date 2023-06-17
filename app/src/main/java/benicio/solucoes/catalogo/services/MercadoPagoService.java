package benicio.solucoes.catalogo.services;

import benicio.solucoes.catalogo.models.MercadoPagoModel;
import benicio.solucoes.catalogo.models.PagamentoPixModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MercadoPagoService {

    @POST("v1/payments")
    Call<MercadoPagoModel> gerar_pix(
            @Header("Authorization") String authorization,
            @Body PagamentoPixModel model);
}
