package com.sju.sju_language_processing.domains.sentences.repository;

import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceInputRepo extends JpaRepository<SentenceInput, Long> {
}
