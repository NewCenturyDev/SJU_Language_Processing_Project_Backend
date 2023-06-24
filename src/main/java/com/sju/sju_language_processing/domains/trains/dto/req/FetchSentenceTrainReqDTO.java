package com.sju.sju_language_processing.domains.trains.dto.req;

import com.sju.sju_language_processing.commons.http.GeneralPageableReqDTO;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchSentenceTrainReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.sentence.id.positive")
    private Long id;
    private EmotionCategory category;
}
