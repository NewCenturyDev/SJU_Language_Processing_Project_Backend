package com.sju.sju_language_processing.domains.trains.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DeleteSentenceTrainReqDTO {
    @NotNull(message = "valid.sentence.id.null")
    @PositiveOrZero(message = "valid.sentence.id.positive")
    private Long id;
}
