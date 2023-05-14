package com.sju.sju_language_processing.domains.musics.dto.req;

import com.sju.sju_language_processing.commons.base.media.dto.request.DeleteMediaReqDTO;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteMusicReqDTO extends DeleteMediaReqDTO {
    @PositiveOrZero(message = "valid.music.id.positive")
    private Long id;
}
