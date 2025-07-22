package survey_alerts.domain;

import lombok.Data;

@Data
public class Alert {
    private String pointOfSale;
    private String description;
    private String product;
    private int type;
    private int priceDiff;

    public String getPointOfSale() {
        return pointOfSale;
    }

    public String getDescription() {
        return description;
    }

    public String getProduct() {
        return product;
    }

    public int getType() {
        return type;
    }

    public int getPriceDiff() {
        return priceDiff;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPriceDiff(int priceDiff) {
        this.priceDiff = priceDiff;
    }
}
