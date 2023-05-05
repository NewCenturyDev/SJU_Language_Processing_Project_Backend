package com.sju.sju_language_processing.domains.sentences.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class SentenceInput {
    @Id
    private Long id;
    private String text;
    private EmotionCategory category;
    private Long musicId;
    private LocalDateTime timestamp;
}
