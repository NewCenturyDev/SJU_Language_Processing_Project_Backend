package com.sju.sju_language_processing.commons.base.media;

import com.sju.sju_language_processing.commons.storage.FileType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class FileMetadata {
    @Enumerated(EnumType.STRING)
    @Column
    protected FileType type;

    @Column(unique = true)
    protected String fileName;

    @Column(unique = true)
    protected String fileURL;

    @Column
    protected Long fileSize;

    @Column(nullable = false)
    protected LocalDateTime timestamp;
}
