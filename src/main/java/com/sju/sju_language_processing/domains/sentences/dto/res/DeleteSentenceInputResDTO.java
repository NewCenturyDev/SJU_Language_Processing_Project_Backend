package com.sju.sju_language_processing.domains.sentences.dto.res;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteSentenceInputResDTO extends GeneralResDTO {
    private Long deletedInputId;
}
