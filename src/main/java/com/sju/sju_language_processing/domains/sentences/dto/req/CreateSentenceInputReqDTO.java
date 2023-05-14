package com.sju.sju_language_processing.domains.sentences.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSentenceInputReqDTO {
    @NotBlank(message = "valid.sentence.text.blank")
    @Size(max = 200, message = "valid.sentence.text.size")
    private String text;
}
