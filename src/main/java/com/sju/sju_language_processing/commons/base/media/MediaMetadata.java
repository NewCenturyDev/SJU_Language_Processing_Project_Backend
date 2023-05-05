package com.sju.sju_language_processing.commons.base.media;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class MediaMetadata extends FileMetadata {
    @Column(nullable = false)
    protected String author;

    @Column(nullable = false)
    protected String artist;
}
