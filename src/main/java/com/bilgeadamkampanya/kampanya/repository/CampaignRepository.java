package com.bilgeadamkampanya.kampanya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bilgeadamkampanya.kampanya.entities.Campaign;
import com.bilgeadamkampanya.kampanya.enums.CampaignCategory;
import com.bilgeadamkampanya.kampanya.enums.CampaignStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    // Find a campaign by its title, description, and category to check for
    // duplicates
    Optional<Campaign> findByTitleAndDescriptionAndCategory(String title, String description,
            CampaignCategory category);

    // Count campaigns by their status for statistics purposes
    @Query("SELECT c.status, COUNT(c), c.id FROM Campaign c GROUP BY c.status, c.id")
    List<Object[]> countCampaignsByStatus();

    // Find all campaigns by status
    List<Campaign> findAllByStatus(CampaignStatus status);
}