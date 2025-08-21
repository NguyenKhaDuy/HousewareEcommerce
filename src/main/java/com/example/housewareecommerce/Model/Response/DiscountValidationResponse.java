package com.example.housewareecommerce.Model.Response;

public class DiscountValidationResponse {
    private boolean valid;
    private String message;
    private String discountCode;
    private Float percentDiscount;

    public DiscountValidationResponse(boolean valid, String message, String discountCode, Float percentDiscount) {
        this.valid = valid;
        this.message = message;
        this.discountCode = discountCode;
        this.percentDiscount = percentDiscount;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDiscountCode() { return discountCode; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }

    public Float getPercentDiscount() { return percentDiscount; }
    public void setPercentDiscount(Float percentDiscount) { this.percentDiscount = percentDiscount; }
}
