package benicio.solucoes.catalogo.models;

public class MercadoPagoModel {
    String id;
    PointOfInteraction point_of_interaction;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PointOfInteraction getPoint_of_interaction() {
        return point_of_interaction;
    }

    public void setPoint_of_interaction(PointOfInteraction point_of_interaction) {
        this.point_of_interaction = point_of_interaction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
