package com.sju.sju_language_processing.domains.sentences.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sju.sju_language_processing.domains.musics.entity.Music;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "valid.sentence.id.positive")
    @Column(name = "sentence_id")
    private Long id;
    @Size(max = 200, message = "valid.sentence.text.size")
    @Column
    private String text;
    @Column
    @Enumerated(EnumType.STRING)
    private EmotionCategory category;
    @Column
    private Long userId;
    @Column
    private String userEmail;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = Music.class)
    @JoinColumn(name = "music_id", referencedColumnName = "music_id")
    private Music music;
    @Column
    @PastOrPresent(message = "valid.sentence.time.past")
    private LocalDateTime timestamp;
}
