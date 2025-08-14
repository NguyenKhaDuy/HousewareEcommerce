package com.example.housewareecommerce.Model.Request;

import java.time.LocalDate;

public class DiscountRequest {
    private Long id;
    private String nameDiscount;
    private String discounCode;
    private Float percentDiscount;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Long statusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDiscount() {
        return nameDiscount;
    }

    public void setNameDiscount(String nameDiscount) {
        this.nameDiscount = nameDiscount;
    }

    public String getDiscounCode() {
        return discounCode;
    }

    public void setDiscounCode(String discounCode) {
        this.discounCode = discounCode;
    }

    public Float getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Float percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
