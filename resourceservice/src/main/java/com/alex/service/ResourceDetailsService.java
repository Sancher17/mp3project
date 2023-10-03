package com.alex.service;


import com.alex.model.ResourceDetails;
import lombok.RequiredArgsConstructor;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

@RequiredArgsConstructor
@Service
public class ResourceDetailsService {

    private final BodyContentHandler handler = new BodyContentHandler();
    private final ParseContext parseContext = new ParseContext();
    private final Mp3Parser parser = new  Mp3Parser();
    private final Metadata metadata = new Metadata();
    
    public ResourceDetails getDetails(MultipartFile file) throws IOException, TikaException, SAXException, UnsupportedFileFormatException {
        InputStream is = file.getInputStream();
        parser.parse(is, handler, metadata, parseContext);
        return ResourceDetails.builder()
                .name(metadata.get("dc:title"))
                .album(metadata.get("xmpDM:album"))
                .artist(metadata.get("xmpDM:artist"))
                .length(getDuration())
                .year(metadata.get("xmpDM:releaseDate"))
                .build();
    }

    private String getDuration() {
        DecimalFormat df = new DecimalFormat("0.00");
        Double doubleDuration = Double.parseDouble(metadata.get("xmpDM:duration")) / 60;
        return df.format(doubleDuration).replace(".", ":");
    }

}
