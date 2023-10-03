package org.alex.repository;

import jakarta.transaction.Transactional;
import org.alex.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
