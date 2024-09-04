package com.bilgeadamkampanya.kampanya;

import com.bilgeadamkampanya.kampanya.DTO.CampaignDTO;
import com.bilgeadamkampanya.kampanya.configuration.TestSecurityConfig;
import com.bilgeadamkampanya.kampanya.controller.CampaignController;
import com.bilgeadamkampanya.kampanya.service.CampaignService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@Import(TestSecurityConfig.class) // Import test-specific security configuration
@WebMvcTest(CampaignController.class)
class CampaignControllerTests {

    @Autowired
    private MockMvc mockMvc; // Automatically wired by WebMvcTest

    @MockBean
    private CampaignService campaignService; // Automatically mocked by WebMvcTest

    @Test
    void testCreateCampaign() throws Exception {
        // Given
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setTitle("Test Campaign");

        // Define the behavior of the mocked campaignService
        Mockito.when(campaignService.createCampaign(any(CampaignDTO.class))).thenReturn(campaignDTO);

        // When & Then
        mockMvc.perform(post("/api/v1.0/kampanya/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Campaign\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Campaign"));
    }
}
