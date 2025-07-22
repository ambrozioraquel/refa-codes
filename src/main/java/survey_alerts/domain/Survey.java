package survey_alerts.domain;


import lombok.Data;

import java.util.List;

@Data
public class Survey {
    private String pointOfSale;
    private String expectedPrice;
    private String product;
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }
    public String getPointOfSale() {
        return pointOfSale;
    }
    public String getExpectedPrice() {
        return expectedPrice;
    }
    public String getProduct() {
        return product;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public void setExpectedPrice(String expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
