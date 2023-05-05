package com.sju.sju_language_processing.commons.base.media.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadMediaReqDTO {
    @NotNull(message = "valid.media.media.file.null")
    protected MultipartFile file;

    @NotBlank(message = "valid.media.author.blank")
    @Size(max = 50, message = "valid.media.author.size")
    protected String author;

    @NotBlank(message = "valid.media.artist.blank")
    @Size(max = 50, message = "valid.media.artist.size")
    protected String artist;
}
