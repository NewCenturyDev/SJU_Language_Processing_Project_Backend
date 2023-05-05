package com.sju.sju_language_processing.domains.musics.dto.res;

import com.sju.sju_language_processing.commons.base.media.dto.response.UploadMediaResDTO;
import com.sju.sju_language_processing.domains.musics.entity.Music;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMusicResDTO extends UploadMediaResDTO<Music> {
    private Music uploadedMusic;
}
