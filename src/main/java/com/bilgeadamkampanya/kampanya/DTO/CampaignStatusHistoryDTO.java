package com.bilgeadamkampanya.kampanya.DTO;

import java.time.LocalDateTime;

import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_status_history")
public class CampaignStatusHistoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long campaignId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus newStatus;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
