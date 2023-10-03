package org.alex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alex.model.Song;
import org.alex.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SongService {

    @Autowired
    private SongRepository repository;

    public Optional<Song> findById(Long id){
        return repository.findById(id);
    }

    public List<Song> findAll(){
        return repository.findAll();
    }

    public Song save(Song song) throws IOException {
        Song save = repository.save(song);
        log.info("Saved: {}", save.getId());
        return save;
    }

    public void deleteByIds(List<Long> ids){
        repository.deleteAllById(ids);
    }

}
