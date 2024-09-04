package com.bilgeadamkampanya.kampanya.enums;

public enum CampaignStatus {
    ONAY_BEKLIYOR("Onay Bekliyor"),
    AKTIF("Aktif"),
    MUKERRER("Mükerrer"),
    DEAKTIF("Deaktif");

    private final String displayName;

    CampaignStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}