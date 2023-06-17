package benicio.solucoes.catalogo.models;

public class PedidoModel {
    String _id,descri,preco,status, link;

    public PedidoModel(String _id, String descri, String preco, String status, String link) {
        this._id = _id;
        this.descri = descri;
        this.preco = preco;
        this.status = status;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
