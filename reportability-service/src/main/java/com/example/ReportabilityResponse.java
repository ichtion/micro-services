package com.example;

/**
 * Created by remis on 04.07.2017.
 */
public class ReportabilityResponse {
    private final String tradeId;
    private final Eligibility eligibility;

    public ReportabilityResponse(String tradeId, Eligibility eligibility) {
        this.tradeId = tradeId;
        this.eligibility = eligibility;
    }

    public String getTradeId() {
        return tradeId;
    }

    public Eligibility getEligibility() {
        return eligibility;
    }
}
