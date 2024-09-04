package com.bilgeadamkampanya.kampanya.enums;

public enum CampaignCategory {
    BIREYSEL_EGITIM("Bireysel Eğitim"),
    GRUP_EGITIMLERI("Grup Eğitimleri"),
    KURUMSAL_EGITIMLER("Kurumsal Eğitimler"),
    ONLINE_EGITIMLER("Online Eğitimler"),
    DIGER("Diğer");

    private final String displayName;

    CampaignCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}