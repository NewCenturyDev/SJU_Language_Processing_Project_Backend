package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.commons.base.media.dto.request.UploadMediaReqDTO;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMusicReqDTO extends UploadMediaReqDTO {
    private String title;
    private EmotionCategory category;
}
