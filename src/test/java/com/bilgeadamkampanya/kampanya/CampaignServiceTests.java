package com.bilgeadamkampanya.kampanya;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bilgeadamkampanya.kampanya.DTO.CampaignDTO;
import com.bilgeadamkampanya.kampanya.DTO.CampaignStatusDTO;
import com.bilgeadamkampanya.kampanya.entities.Campaign;
import com.bilgeadamkampanya.kampanya.enums.CampaignCategory;
import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;
import com.bilgeadamkampanya.kampanya.exceptions.CustomException;
import com.bilgeadamkampanya.kampanya.repository.CampaignRepository;
import com.bilgeadamkampanya.kampanya.repository.CampaignStatusHistoryRepository;
import com.bilgeadamkampanya.kampanya.service.CampaignService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CampaignServiceTests {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CampaignStatusHistoryRepository campaignStatusHistoryRepository;

    @InjectMocks
    private CampaignService campaignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDuplicateCampaignThrowsException() {
        // Given
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setTitle("Test Campaign");
        campaignDTO.setDescription("This is a test campaign");
        campaignDTO.setCategory(CampaignCategory.BIREYSEL_EGITIM);

        when(campaignRepository.findByTitleAndDescriptionAndCategory(
                campaignDTO.getTitle(), campaignDTO.getDescription(), campaignDTO.getCategory()))
                .thenReturn(Optional.of(new Campaign()));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            campaignService.createCampaign(campaignDTO);
        });

        assertEquals("Duplicate campaign detected", exception.getMessage());
    }

    @Test
    void testUpdateCampaignStatusMUKERRERThrowsException() {
        // Given
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setStatus(CampaignStatus.MUKERRER);

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        CampaignStatusDTO campaignStatusDTO = new CampaignStatusDTO(1L, CampaignStatus.AKTIF, null);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            campaignService.updateCampaignStatus(campaignStatusDTO);
        });

        assertEquals("Mükerrer campaign cannot be updated", exception.getMessage());
    }

}
