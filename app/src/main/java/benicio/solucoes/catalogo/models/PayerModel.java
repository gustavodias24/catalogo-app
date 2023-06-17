package benicio.solucoes.catalogo.models;

public class PayerModel {

    String email;

    public PayerModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
