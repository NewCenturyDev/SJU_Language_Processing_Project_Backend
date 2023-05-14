package com.sju.sju_language_processing.domains.sentences.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateSentenceInputReqDTO {
    @NotNull(message = "valid.sentence.id.null")
    @PositiveOrZero(message = "valid.sentence.id.positive")
    private Long id;
    @NotNull(message = "valid.sentence.category.null")
    private EmotionCategory category;
}
