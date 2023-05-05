package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import lombok.Data;

@Data
public class UpdateMusicCategoryReqDTO {
    private Long id;
    private EmotionCategory category;
}
