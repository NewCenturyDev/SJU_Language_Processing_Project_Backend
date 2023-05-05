package com.sju.sju_language_processing.domains.sentences.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import lombok.Data;

@Data
public class UpdateSentenceInputReqDTO {
    private Long id;
    private EmotionCategory category;
}
