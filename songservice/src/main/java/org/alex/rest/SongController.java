package org.alex.rest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alex.model.DeleteResponse;
import org.alex.model.Song;
import org.alex.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongController {

    private static final String IDS_SEPARATOR = ",";
    public static final String ERROR_MESSAGE_404 = "The resource with the specified id does not exist";

    @Autowired
    private SongService songService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id, HttpServletResponse response) throws SQLException, IOException {
        Optional<Song> song = songService.findById(id);
        if (song.isPresent()) {
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ERROR_MESSAGE_404, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getAllIds() {
        List<Song> all = songService.findAll();
        List<Long> ids = all.stream().map(Song::getId).toList();
        return ResponseEntity.ok(ids);
    }

    @PostMapping()
    public ResponseEntity<Song> save(@RequestBody() Song song) throws IOException {
        Song songSaved = songService.save(song);
        return new ResponseEntity<>(songSaved, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<DeleteResponse> deleteByIds(@RequestParam("id")
            @NotBlank @Size(max = 200) String ids) {
        log.info("Delete by IDs: {}", ids);
        List<Long> idsToDelete = Arrays.stream(ids.split(IDS_SEPARATOR))
                .map(Long::parseLong).toList();
        songService.deleteByIds(idsToDelete);
        return new ResponseEntity<>(DeleteResponse.builder().ids(idsToDelete).build(), HttpStatus.OK);
    }
}