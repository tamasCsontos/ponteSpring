package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class ImageStore {


    private final long maxSize = 2000000;
    //maxSize in byte

    private final ImageRepository imageRepository;

    public Iterable<ImageMeta> findAll() {
        return imageRepository.findAll();
    }

    public String save(MultipartFile request) {

        log.info("name " + request.getOriginalFilename());
        log.info("size " + request.getSize());
        if (request.getSize() <= maxSize) {
            ImageMeta image = null;
            try {
                image = ImageMeta.builder()
                        .name(request.getOriginalFilename())
                        .mimeType(request.getContentType())
                        .size(request.getSize())
                        .data(request.getBytes())
                        .build();

                log.info(image.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
            imageRepository.save(image);

            return "ok";

        } else return "Image size is above 2 mb";

    }


}
