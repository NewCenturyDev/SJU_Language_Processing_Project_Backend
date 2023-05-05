package com.sju.sju_language_processing.domains.sentences.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;

import java.time.LocalDateTime;

public class FetchSentenceInputReqDTO {
    private Long id;
    private EmotionCategory category;
    private Long musicId;
    private LocalDateTime timeRangeFrom;
    private LocalDateTime timeRangeTo;
}
