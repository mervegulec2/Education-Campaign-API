package com.bilgeadamkampanya.kampanya.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bilgeadamkampanya.kampanya.DTO.CampaignDTO;
import com.bilgeadamkampanya.kampanya.DTO.CampaignStatusDTO;
import com.bilgeadamkampanya.kampanya.DTO.CampaignStatusHistoryDTO;
import com.bilgeadamkampanya.kampanya.entities.Campaign;
import com.bilgeadamkampanya.kampanya.entities.CampaignStatusHistory;
import com.bilgeadamkampanya.kampanya.enums.CampaignCategory;
import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;
import com.bilgeadamkampanya.kampanya.exceptions.CustomException;
import com.bilgeadamkampanya.kampanya.repository.CampaignRepository;
import com.bilgeadamkampanya.kampanya.repository.CampaignStatusHistoryRepository;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignStatusHistoryRepository campaignStatusHistoryRepository;

    public CampaignService(CampaignRepository campaignRepository,
            CampaignStatusHistoryRepository campaignStatusHistoryRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignStatusHistoryRepository = campaignStatusHistoryRepository;
    }

    // 1. Create a new campaign
    public CampaignDTO createCampaign(CampaignDTO campaignDTO) {
        // Check for duplicates
        if (campaignRepository.findByTitleAndDescriptionAndCategory(campaignDTO.getTitle(),
                campaignDTO.getDescription(), campaignDTO.getCategory()).isPresent()) {
            // If duplicate, set the status as MUKERRER
            Campaign campaign = new Campaign();
            BeanUtils.copyProperties(campaignDTO, campaign);
            campaign.setStatus(CampaignStatus.MUKERRER);
            campaign.setCreatedDate(LocalDateTime.now());
            campaign.setUpdatedDate(LocalDateTime.now());

            Campaign savedCampaign = campaignRepository.save(campaign);
            campaignDTO.setId(savedCampaign.getId());
            campaignDTO.setStatus(savedCampaign.getStatus());

            // Record the status as MUKERRER in history
            recordStatusHistory(savedCampaign.getId(), null, CampaignStatus.MUKERRER);

            return campaignDTO;
        }

        Campaign campaign = new Campaign();
        BeanUtils.copyProperties(campaignDTO, campaign);
        campaign.setStatus(determineInitialStatus(campaignDTO.getCategory()));
        campaign.setCreatedDate(LocalDateTime.now());
        campaign.setUpdatedDate(LocalDateTime.now());

        Campaign savedCampaign = campaignRepository.save(campaign);
        campaignDTO.setId(savedCampaign.getId());
        campaignDTO.setStatus(savedCampaign.getStatus());

        // Record the initial status in history
        recordStatusHistory(savedCampaign.getId(), null, savedCampaign.getStatus());

        return campaignDTO;
    }

    // 2. Update the status of an existing campaign
    public CampaignDTO updateCampaignStatus(CampaignStatusDTO campaignStatusDTO) {
        Campaign campaign = campaignRepository.findById(campaignStatusDTO.getCampaignId())
                .orElseThrow(() -> new CustomException("Campaign not found", HttpStatus.NOT_FOUND.value()));

        if (campaign.getStatus() == CampaignStatus.MUKERRER) {
            throw new CustomException("Mükerrer campaign cannot be updated", HttpStatus.FORBIDDEN.value());
        }

        CampaignStatus oldStatus = campaign.getStatus();
        campaign.setStatus(campaignStatusDTO.getNewStatus());
        campaign.setUpdatedDate(LocalDateTime.now());

        Campaign updatedCampaign = campaignRepository.save(campaign);

        // Record the status change in history
        recordStatusHistory(campaign.getId(), oldStatus, campaign.getStatus());

        CampaignDTO updatedCampaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(updatedCampaign, updatedCampaignDTO);
        return updatedCampaignDTO;
    }

    // 3. Retrieve campaign statistics (count by status)
    public List<CampaignStatusDTO> getCampaignStatistics() {
        List<Object[]> stats = campaignRepository.countCampaignsByStatus();
        return stats.stream()
                .map(stat -> new CampaignStatusDTO(
                        null, // Campaign ID'sine gerek yok
                        (CampaignStatus) stat[0],
                        ((Number) stat[1]).longValue() // Duruma göre kampanya sayısı
                ))
                .collect(Collectors.toList());
    }

    // Bonus: Get the status history of a specific campaign
    public List<CampaignStatusHistoryDTO> getCampaignStatusHistory(Long campaignId) {
        List<CampaignStatusHistory> history = campaignStatusHistoryRepository.findByCampaignId(campaignId);
        return history.stream()
                .map(hist -> {
                    CampaignStatusHistoryDTO dto = new CampaignStatusHistoryDTO();
                    BeanUtils.copyProperties(hist, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Helper method to determine the initial status based on the campaign category
    private CampaignStatus determineInitialStatus(CampaignCategory category) {
        if (category == CampaignCategory.BIREYSEL_EGITIM || category == CampaignCategory.ONLINE_EGITIMLER
                || category == CampaignCategory.DIGER) {
            return CampaignStatus.ONAY_BEKLIYOR;
        }
        return CampaignStatus.AKTIF;
    }

    // Helper method to record status changes in history
    private void recordStatusHistory(Long campaignId, CampaignStatus oldStatus, CampaignStatus newStatus) {
        if (oldStatus == null) {
            oldStatus = CampaignStatus.ONAY_BEKLIYOR; // Veya başka bir uygun varsayılan durum
        }

        CampaignStatusHistory history = new CampaignStatusHistory();
        history.setCampaignId(campaignId);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setTimestamp(LocalDateTime.now());
        campaignStatusHistoryRepository.save(history);
    }

}