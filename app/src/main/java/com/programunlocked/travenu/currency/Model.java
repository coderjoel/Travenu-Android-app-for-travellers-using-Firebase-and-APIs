package com.programunlocked.travenu.currency;

/**
 * Created by uddishverma on 11/06/17.
 */

public class Model {

    private String countryCode;
    private String currencyCode;

    public Model(String countryCode, String currencyCode) {
        this.countryCode = countryCode;
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
