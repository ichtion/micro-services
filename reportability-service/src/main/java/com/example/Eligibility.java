package com.example;

/**
 * Created by remis on 04.07.2017.
 */
public class Eligibility {
    private String tradeId;
    private String eligibilityStatus;

    public Eligibility(String tradeId, String eligibilityStatus) {
        this.tradeId = tradeId;
        this.eligibilityStatus = eligibilityStatus;
    }

    public Eligibility() { }

    public String getTradeId() {
        return tradeId;
    }

    public String getEligibilityStatus() {
        return eligibilityStatus;
    }
}
