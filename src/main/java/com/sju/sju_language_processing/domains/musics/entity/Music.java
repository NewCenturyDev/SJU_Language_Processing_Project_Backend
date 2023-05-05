package com.sju.sju_language_processing.domains.musics.entity;

import com.sju.sju_language_processing.commons.base.media.MediaMetadata;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Music extends MediaMetadata {
    @Id
    private Long id;
    private String title;
    private EmotionCategory category;
}
