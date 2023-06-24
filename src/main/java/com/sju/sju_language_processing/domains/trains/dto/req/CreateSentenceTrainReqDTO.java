package com.sju.sju_language_processing.domains.trains.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSentenceTrainReqDTO {
    @NotBlank(message = "valid.sentence.language.blank")
    private String language;

    @NotNull(message = "valid.sentence.category.blank")
    private EmotionCategory category;

    @NotBlank(message = "valid.sentence.text.blank")
    @Size(max = 5000, message = "valid.sentence.text.size")
    private String text;
}
