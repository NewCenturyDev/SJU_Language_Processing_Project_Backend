package com.sju.sju_language_processing.commons.base.media.dto.response;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMediaResDTO<T> extends GeneralResDTO {
    protected T fileInfo;
}
