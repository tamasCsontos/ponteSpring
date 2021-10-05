package hu.ponte.hr.services;

import hu.ponte.hr.controller.Image;
import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.repository.ImageJpaEntity;
import hu.ponte.hr.repository.ImageJpaRepository;
import hu.ponte.hr.repository.ImageMetaJpaEntity;
import hu.ponte.hr.repository.ImageMetaJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.PrivateKey;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class ImageStore {


    private final long maxSize = 2000000;
    //maxSize in byte

    private final String privateKeyFileName = "key.private";

    private final ImageJpaRepository imageJpaRepository;

    private final ImageMetaJpaRepository imageMetaJpaRepository;

    private SignService signService;




    public Image findById(Long imageId) throws NoSuchElementException {
        ImageJpaEntity imageJpaEntity = imageJpaRepository.findById(imageId)
                .orElseThrow(() -> new NoSuchElementException("No image with" + " " + imageId));

        ImageMetaJpaEntity imageMetaJpaEntity = imageJpaEntity.getImageMetaJpaEntity();

        return Image.builder()
                .id(String.valueOf(imageJpaEntity.getId()))
                .imageMeta(ImageMeta.builder()
                        .id(String.valueOf(imageMetaJpaEntity.getId()))
                        .name(imageMetaJpaEntity.getName())
                        .mimeType(imageMetaJpaEntity.getMimeType())
                        .size(imageMetaJpaEntity.getSize())
                        .digitalSign(imageMetaJpaEntity.getDigitalSign())
                        .build())
                .data(imageJpaEntity.getData())
                .build();
    }

    public String save(MultipartFile request) {

        log.info("name " + request.getOriginalFilename());
        log.info("size " + request.getSize());

        if (!request.isEmpty()) {
            if (request.getSize() <= maxSize) {

                try {

                    PrivateKey privateKey = signService.getPrivateKey(privateKeyFileName);
                    ImageMetaJpaEntity imageMetaJpaEntity = ImageMetaJpaEntity.builder()
                            .name(request.getOriginalFilename())
                            .mimeType(request.getContentType())
                            .size(request.getSize())
                            .digitalSign(signService.sign(request.getBytes(), privateKey))
                            .build();

                    ImageJpaEntity imageJpaEntity = ImageJpaEntity.builder()
                            .imageMetaJpaEntity(imageMetaJpaEntity)
                            .data(request.getBytes())
                            .build();

                    imageJpaRepository.save(imageJpaEntity);
                    return "ok";

                } catch (Exception e) {
                    log.error("",e);
                    return e.toString();
                }

            } else return "Image size is above 2 mb";
        }

        return "no image";
    }

}
