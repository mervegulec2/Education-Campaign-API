package com.bilgeadamkampanya.kampanya.DTO;

import java.time.LocalDateTime;

import com.bilgeadamkampanya.kampanya.enums.CampaignCategory;
import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {
    private Long id;
    private String title;
    private String description;
    private CampaignCategory category;
    private CampaignStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
