package com.sju.sju_language_processing.domains.trains.dto.res;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import com.sju.sju_language_processing.domains.trains.entity.SentenceTrain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSentenceTrainResDTO extends GeneralResDTO {
    SentenceTrain updatedTrain;
}
