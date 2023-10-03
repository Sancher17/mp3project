package com.alex.rest;

import com.alex.exception.Mp3FormatException;
import com.alex.model.DeleteResponse;
import com.alex.model.Resource;
import com.alex.service.ResourceService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private static final String IDS_SEPARATOR = ",";
    public static final String ERROR_MESSAGE_400 = "Validation failed or request body is invalid MP3";
    public static final String ERROR_MESSAGE_500 = "An internal server error has occurred";
    public static final String ERROR_MESSAGE_404 = "The resource with the specified id does not exist";

    private final ResourceService resourceService;

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id, HttpServletResponse response) throws SQLException, IOException {
        log.info("Request for ID: {}", id);
        Optional<Resource> resource = resourceService.findById(id);
        if (resource.isPresent()) {
            Resource resourceFromDb = resource.get();
            response.addHeader("Content-Disposition", "attachment; filename=" + resourceFromDb.getFileName());
            IOUtils.copy(resourceFromDb.getData().getBinaryStream(), response.getOutputStream());
        } else {
            return new ResponseEntity<>(ERROR_MESSAGE_404, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getAllIds() {
        List<Resource> all = resourceService.findAll();
        List<Long> ids = all.stream().map(Resource::getId).toList();
        return ResponseEntity.ok(ids);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestParam("file") MultipartFile file) {
        Resource resource;
        try {
            resource = resourceService.save(file);
            resource.setData(null);
            resource.setFileName(null);
        } catch (Mp3FormatException e) {
            return new ResponseEntity<>(ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(ERROR_MESSAGE_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<DeleteResponse> deleteByIds(@RequestParam("id")
            @NotBlank @Size(max = 200) String ids) {
        List<Long> idsToDelete = Arrays.stream(ids.split(IDS_SEPARATOR))
                .map(Long::parseLong).toList();
        resourceService.deleteByIds(idsToDelete);
        return new ResponseEntity<>(DeleteResponse.builder().ids(idsToDelete).build(),
                HttpStatus.OK);
    }
}