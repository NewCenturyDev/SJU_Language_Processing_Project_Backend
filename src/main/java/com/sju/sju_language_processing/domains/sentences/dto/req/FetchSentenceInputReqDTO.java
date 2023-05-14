package com.sju.sju_language_processing.domains.sentences.dto.req;

import com.sju.sju_language_processing.commons.http.GeneralPageableReqDTO;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchSentenceInputReqDTO extends GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.sentence.id.positive")
    private Long id;
    private EmotionCategory category;
    @PositiveOrZero(message = "valid.music.id.positive")
    private Long musicId;
    @PastOrPresent(message = "valid.sentence.time.past")
    private LocalDateTime timeRangeFrom;
    @PastOrPresent(message = "valid.sentence.time.past")
    private LocalDateTime timeRangeTo;
}
