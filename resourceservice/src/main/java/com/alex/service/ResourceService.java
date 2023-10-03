package com.alex.service;

import com.alex.exception.Mp3FormatException;
import com.alex.model.Resource;
import com.alex.model.ResourceDetails;
import com.alex.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResourceService {

    private final ResourceRepository repository;
    private final LobService lobService;
    private final ResourceDetailsService resourceDetailsService;
    private final RestService restService;
    private final Tika tika = new Tika();

    public Optional<Resource> findById(Long id){
        return repository.findById(id);
    }

    public List<Resource> findAll(){
        return repository.findAll();
    }

    public Resource save(MultipartFile file) throws IOException, TikaException, SAXException {
        InputStream is = file.getInputStream();
        validateFormat(is);

        String filename = file.getOriginalFilename();
        Blob blob = lobService.createBlob(is, file.getSize());

        Resource resource = new Resource();
        resource.setData(blob);
        resource.setFileName(filename);
        Resource resourceSaved = repository.save(resource);
        log.info("Saved: {}", resourceSaved.getId());

        ResourceDetails details = resourceDetailsService.getDetails(file);
        details.setResourceId(resourceSaved.getId());
        restService.send(details);
        return resourceSaved;
    }

    public void deleteByIds(List<Long> ids){
        repository.deleteAllById(ids);
    }

    private void validateFormat(InputStream is) throws IOException {
        String type = tika.detect(is);
        if (!type.equals("audio/mpeg")) {
            log.error("Invalid file format");
            throw new Mp3FormatException();
        }
    }

}
