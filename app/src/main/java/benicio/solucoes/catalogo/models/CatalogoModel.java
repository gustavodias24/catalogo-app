package benicio.solucoes.catalogo.models;

public class CatalogoModel {
    int base, max, min, subType, type;
    String descri, title, url_photo;

    public CatalogoModel(int base, int max, int min, int subType, int type, String descri, String title, String url_photo) {
        this.base = base;
        this.max = max;
        this.min = min;
        this.subType = subType;
        this.type = type;
        this.descri = descri;
        this.title = title;
        this.url_photo = url_photo;
    }

    public CatalogoModel() {
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }
}
