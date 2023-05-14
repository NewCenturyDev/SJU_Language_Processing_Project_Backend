package com.sju.sju_language_processing.domains.musics.entity;

import com.sju.sju_language_processing.commons.base.media.MediaMetadata;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Music extends MediaMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    @Size(max = 100, message = "valid.music.title.size")
    @Column
    private String title;

    @Enumerated(EnumType.STRING)
    @Column
    private EmotionCategory category;
}
