package com.sju.sju_language_processing.domains.sentences.dto.res;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateSentenceInputResDTO extends GeneralResDTO {
    private SentenceInput createdInput;
}
