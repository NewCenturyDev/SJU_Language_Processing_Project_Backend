package com.sju.sju_language_processing.domains.musics.service;

import com.sju.sju_language_processing.commons.base.media.FileMetadata;
import com.sju.sju_language_processing.commons.storage.FileType;
import com.sju.sju_language_processing.commons.storage.StorageService;
import com.sju.sju_language_processing.domains.musics.dto.req.UpdateMusicCategoryReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.req.UploadMusicReqDTO;
import com.sju.sju_language_processing.domains.musics.entity.Music;
import com.sju.sju_language_processing.domains.musics.repository.MusicRepo;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class MusicCrudServ extends MusicLogicServ implements MusicCrudInterface {
    private final StorageService storageServ;

    public MusicCrudServ(MusicRepo musicRepo, StorageService storageServ) {
        super(musicRepo);
        this.storageServ = storageServ;
    }

    @Transactional
    public Music createMusic(UploadMusicReqDTO reqDTO) throws Exception {
        MultipartFile file = reqDTO.getFile();

        Music music = Music.builder()
                .title(reqDTO.getTitle())
                .category(reqDTO.getCategory())
                .artist(reqDTO.getArtist())
                .author(reqDTO.getAuthor())
                .timestamp(LocalDateTime.now())
                .build();
        music = musicRepo.save(music);

        try {
            storageServ.createEntityStorage("music", music.getId());
            FileMetadata metadata = this.storageServ.saveFile(file, FileType.MEDIA_AUDIO, "music", music.getId());
            music.setType(metadata.getType());
            music.setFileName(metadata.getFileName());
            music.setFileSize(metadata.getFileSize());
            music.setFileURL(metadata.getFileURL());
        } catch (Exception ignored) {
            musicRepo.delete(music);
            throw new Exception(msgSrc.getMessage("error.music.upload.fail", null, Locale.ENGLISH));
        }

        return music;
    }

    public Music fetchRandomMusicByEmotion(EmotionCategory emotion) throws Exception {
        return musicRepo.findRandomMusicByCategory(emotion.toString()).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.music.notExist", null, Locale.ENGLISH))
        );
    }

    @Transactional
    public Music updateMusicCategory(UpdateMusicCategoryReqDTO reqDTO) throws Exception {
        Music target = musicRepo.findById(reqDTO.getId()).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.music.notExist", null, Locale.ENGLISH))
        );

        target.setCategory(reqDTO.getCategory());
        return target;
    }

    @Transactional
    public Long deleteMusic(Long musicId) throws Exception {
        Music target = musicRepo.findById(musicId).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.music.notExist", null, Locale.ENGLISH))
        );

        musicRepo.delete(target);
        return target.getId();
    }
}
