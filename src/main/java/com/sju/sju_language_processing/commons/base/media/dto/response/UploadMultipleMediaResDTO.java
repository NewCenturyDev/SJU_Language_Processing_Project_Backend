package com.sju.sju_language_processing.commons.base.media.dto.response;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMultipleMediaResDTO<T> extends GeneralResDTO {
    protected List<T> fileInfos;
}
