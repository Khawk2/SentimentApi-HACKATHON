package com.example.demo.repository;

import com.example.demo.model.SentimentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, Long> {

    @Query("SELECT s FROM SentimentAnalysis s ORDER BY s.createdAt DESC")
    List<SentimentAnalysis> findRecentAnalyses();

    long countByPrevision(String prevision);
}
