package com.sju.sju_language_processing.domains.sentences.dto.res;

import com.sju.sju_language_processing.commons.http.GeneralPageableResDTO;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchSentenceInputResDTO extends GeneralPageableResDTO {
    SentenceInput sentenceInput;
    List<SentenceInput> sentenceInputs;
}
