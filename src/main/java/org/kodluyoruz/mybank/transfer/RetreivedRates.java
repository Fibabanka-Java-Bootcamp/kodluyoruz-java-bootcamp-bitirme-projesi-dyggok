package org.kodluyoruz.mybank.transfer;

import java.io.Serializable;
import java.util.HashMap;


public class RetreivedRates implements Serializable {

    private HashMap rates = new HashMap<String, Double>();

    private String base;

    private String date;

    public HashMap getRates() {
        return rates;
    }

    public void setRates(HashMap rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

