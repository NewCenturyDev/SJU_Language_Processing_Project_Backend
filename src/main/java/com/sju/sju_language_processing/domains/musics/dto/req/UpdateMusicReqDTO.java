package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateMusicReqDTO {
    @NotNull(message = "valid.music.id.null")
    @PositiveOrZero(message = "valid.music.id.positive")
    private Long id;
    @NotBlank(message = "valid.music.title.blank")
    @Size(max = 100, message = "valid.music.title.size")
    private String title;
    @NotBlank(message = "valid.media.author.blank")
    @Size(max = 50, message = "valid.media.author.size")
    protected String author;
    @NotBlank(message = "valid.media.artist.blank")
    @Size(max = 50, message = "valid.media.artist.size")
    protected String artist;
    @NotNull(message = "valid.music.category.null")
    private EmotionCategory category;
}
