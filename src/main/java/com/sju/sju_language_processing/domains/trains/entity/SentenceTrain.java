package com.sju.sju_language_processing.domains.trains.entity;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Locale;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceTrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "valid.sentence.id.positive")
    @Column(name = "sentence_id")
    private Long id;

    @Column(nullable = false)
    private Locale language;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionCategory category;

    @Size(max = 5000, message = "valid.sentence.text.size")
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer sentiment;
}
