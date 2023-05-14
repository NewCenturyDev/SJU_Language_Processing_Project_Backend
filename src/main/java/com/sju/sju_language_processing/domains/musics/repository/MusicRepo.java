package com.sju.sju_language_processing.domains.musics.repository;

import com.sju.sju_language_processing.domains.musics.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepo extends JpaRepository<Music, Long> {
    @Query(value = "SELECT * FROM music WHERE music.category = :category ORDER BY RAND() LIMIT 1;", nativeQuery = true)
    Optional<Music> findRandomMusicByCategory(@Param("category") String category);
}
