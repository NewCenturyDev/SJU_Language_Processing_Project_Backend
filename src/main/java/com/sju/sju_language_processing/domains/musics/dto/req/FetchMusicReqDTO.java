package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FetchMusicReqDTO {
    @NotNull(message = "valid.music.category.null")
    private EmotionCategory category;
}
