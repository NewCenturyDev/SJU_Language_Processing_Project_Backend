package com.sju.sju_language_processing.domains.trains.repository;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.trains.entity.SentenceTrain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceTrainRepo extends JpaRepository<SentenceTrain, Long> {
    Page<SentenceTrain> findAllByCategory(EmotionCategory emotion, Pageable pageable);
}
