package benicio.solucoes.catalogo.models;

public class PagamentoPixModel {
     Number transaction_amount;
     String payment_method_id, description;
     PayerModel payer;

    public PagamentoPixModel(Number transaction_amount, String payment_method_id, String description, PayerModel payer) {
        this.transaction_amount = transaction_amount;
        this.payment_method_id = payment_method_id;
        this.description = description;
        this.payer = payer;
    }

    public Number getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(Number transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PayerModel getPayer() {
        return payer;
    }

    public void setPayer(PayerModel payer) {
        this.payer = payer;
    }
}
