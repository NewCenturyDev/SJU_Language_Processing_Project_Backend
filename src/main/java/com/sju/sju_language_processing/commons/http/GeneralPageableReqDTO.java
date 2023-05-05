package com.sju.sju_language_processing.commons.http;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class GeneralPageableReqDTO {
    @PositiveOrZero(message = "valid.page.positive")
    private Integer pageIdx;

    @PositiveOrZero(message = "valid.page.positive")
    private Integer pageLimit;
}
