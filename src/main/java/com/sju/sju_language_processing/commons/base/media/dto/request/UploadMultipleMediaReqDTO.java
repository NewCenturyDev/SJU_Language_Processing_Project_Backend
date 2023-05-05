package com.sju.sju_language_processing.commons.base.media.dto.request;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadMultipleMediaReqDTO extends GeneralResDTO {
    @NotEmpty(message = "valid.media.file.empty")
    protected List<MultipartFile> files;

    @NotBlank(message = "valid.media.author.blank")
    @Size(max = 50, message = "valid.media.author.size")
    protected String author;

    @NotBlank(message = "valid.media.artist.blank")
    @Size(max = 50, message = "valid.media.artist.size")
    protected String translator;
}
