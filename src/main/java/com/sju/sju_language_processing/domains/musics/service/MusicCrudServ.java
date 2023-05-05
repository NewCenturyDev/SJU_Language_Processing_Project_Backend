package com.sju.sju_language_processing.domains.musics.service;

import com.sju.sju_language_processing.domains.musics.repository.MusicRepo;
import org.springframework.stereotype.Service;

@Service
public class MusicCrudServ extends MusicLogicServ {
    public MusicCrudServ(MusicRepo musicRepo) {
        super(musicRepo);
    }
}
