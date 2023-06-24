package com.sju.sju_language_processing.domains.trains.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateSentenceTrainReqDTO {
    @NotNull(message = "valid.sentence.id.null")
    @PositiveOrZero(message = "valid.sentence.id.positive")
    private Long id;

    @NotNull(message = "valid.sentence.category.null")
    private EmotionCategory category;

    @NotBlank(message = "valid.sentence.text.blank")
    @Size(max = 5000, message = "valid.sentence.text.size")
    private String text;
}
