package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateMusicCategoryReqDTO {
    @NotNull(message = "valid.music.id.null")
    @PositiveOrZero(message = "valid.music.id.positive")
    private Long id;
    @NotNull(message = "valid.music.category.null")
    private EmotionCategory category;
}
