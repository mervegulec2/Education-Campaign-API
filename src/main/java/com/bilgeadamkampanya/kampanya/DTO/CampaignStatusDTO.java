package com.bilgeadamkampanya.kampanya.DTO;

import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignStatusDTO {
    private Long campaignId;
    private CampaignStatus newStatus;
    private Long count;
}
