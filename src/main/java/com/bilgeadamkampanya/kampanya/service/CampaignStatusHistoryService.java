package com.bilgeadamkampanya.kampanya.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bilgeadamkampanya.kampanya.DTO.CampaignStatusHistoryDTO;
import com.bilgeadamkampanya.kampanya.entities.CampaignStatusHistory;
import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;
import com.bilgeadamkampanya.kampanya.repository.CampaignStatusHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignStatusHistoryService {

    private final CampaignStatusHistoryRepository campaignStatusHistoryRepository;

    public CampaignStatusHistoryService(CampaignStatusHistoryRepository campaignStatusHistoryRepository) {
        this.campaignStatusHistoryRepository = campaignStatusHistoryRepository;
    }

    // Method to record a status change in the history
    public void recordStatusChange(Long campaignId, CampaignStatus oldStatus, CampaignStatus newStatus) {
        CampaignStatusHistory history = new CampaignStatusHistory();
        history.setCampaignId(campaignId);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setTimestamp(LocalDateTime.now());

        campaignStatusHistoryRepository.save(history);
    }

    // Method to retrieve the status history of a specific campaign
    public List<CampaignStatusHistoryDTO> getStatusHistory(Long campaignId) {
        List<CampaignStatusHistory> history = campaignStatusHistoryRepository.findByCampaignId(campaignId);
        return history.stream()
                .map(hist -> {
                    CampaignStatusHistoryDTO dto = new CampaignStatusHistoryDTO();
                    BeanUtils.copyProperties(hist, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
