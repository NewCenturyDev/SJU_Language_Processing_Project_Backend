package com.sju.sju_language_processing.domains.sentences.repository;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SentenceInputRepo extends JpaRepository<SentenceInput, Long> {
    Page<SentenceInput> findAllByCategory(EmotionCategory emotion, Pageable pageable);
    Page<SentenceInput> findAllByMusicId(Long musicId, Pageable pageable);
    List<SentenceInput> findAllByTimestampAfterAndTimestampBefore(LocalDateTime from, LocalDateTime to);
}
