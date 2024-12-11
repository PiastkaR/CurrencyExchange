package com.nn.domain.model;

public class RateBuilder {
    private String no;
    private String effectiveDate;
    private String mid;


    public RateBuilder withNo(String no) {
        this.no = no;
        return this;
    }

    public RateBuilder withEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public RateBuilder withMid(String mid) {
        this.mid = mid;
        return this;
    }

    public Rate build() {
        return new Rate(no, effectiveDate, mid);
    }

}
