package com.bilgeadamkampanya.kampanya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bilgeadamkampanya.kampanya.DTO.CampaignDTO;
import com.bilgeadamkampanya.kampanya.DTO.CampaignStatusDTO;
import com.bilgeadamkampanya.kampanya.DTO.CampaignStatusHistoryDTO;
import com.bilgeadamkampanya.kampanya.service.CampaignService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/kampanya")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // Endpoint to create a new campaign
    @PostMapping("/create")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        CampaignDTO createdCampaign = campaignService.createCampaign(campaignDTO);
        return ResponseEntity.ok(createdCampaign);
    }

    // Endpoint to update the status of a campaign
    @PostMapping("/updateState")
    public ResponseEntity<CampaignDTO> updateCampaignState(@RequestBody CampaignStatusDTO campaignStatusDTO) {
        CampaignDTO updatedCampaign = campaignService.updateCampaignStatus(campaignStatusDTO);
        return ResponseEntity.ok(updatedCampaign);
    }

    // Endpoint to get campaign statistics
    @GetMapping("/statistics")
    public ResponseEntity<List<CampaignStatusDTO>> getCampaignStatistics() {
        List<CampaignStatusDTO> statistics = campaignService.getCampaignStatistics();
        return ResponseEntity.ok(statistics);
    }

    // Bonus: Endpoint to get status history of a campaign
    @GetMapping("/{campaignId}/statusHistory")
    public ResponseEntity<List<CampaignStatusHistoryDTO>> getCampaignStatusHistory(@PathVariable Long campaignId) {
        List<CampaignStatusHistoryDTO> statusHistory = campaignService.getCampaignStatusHistory(campaignId);
        return ResponseEntity.ok(statusHistory);
    }
}