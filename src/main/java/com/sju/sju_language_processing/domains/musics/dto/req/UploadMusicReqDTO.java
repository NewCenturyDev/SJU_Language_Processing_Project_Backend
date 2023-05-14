package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.commons.base.media.dto.request.UploadMediaReqDTO;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMusicReqDTO extends UploadMediaReqDTO {
    @NotBlank(message = "valid.music.title.blank")
    @Size(max = 100, message = "valid.music.title.size")
    private String title;
    @NotNull(message = "valid.music.category.null")
    private EmotionCategory category;
}
