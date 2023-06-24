package com.sju.sju_language_processing.domains.trains.dto.res;

import com.sju.sju_language_processing.commons.http.GeneralPageableResDTO;
import com.sju.sju_language_processing.domains.trains.entity.SentenceTrain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FetchSentenceTrainResDTO extends GeneralPageableResDTO {
    SentenceTrain sentenceTrain;
    List<SentenceTrain> sentenceTrains;
}
