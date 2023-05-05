package com.sju.sju_language_processing.domains.musics.repository;

import com.sju.sju_language_processing.domains.musics.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepo extends JpaRepository<Music, Long> {
}
