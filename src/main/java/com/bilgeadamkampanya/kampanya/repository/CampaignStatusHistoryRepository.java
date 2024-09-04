package com.bilgeadamkampanya.kampanya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bilgeadamkampanya.kampanya.entities.CampaignStatusHistory;

import java.util.List;

@Repository
public interface CampaignStatusHistoryRepository extends JpaRepository<CampaignStatusHistory, Long> {

    // Find all status history entries by campaign ID
    List<CampaignStatusHistory> findByCampaignId(Long campaignId);
}