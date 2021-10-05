package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.PrivateKey;

import java.io.IOException;

@Log4j2
@AllArgsConstructor
@Service
public class ImageStore {


    private final long maxSize = 2000000;
    //maxSize in byte

    private final String privateKeyFileName = "key.private";

    private final ImageRepository imageRepository;



    public Iterable<ImageMeta> findAll() {
        return imageRepository.findAll();
    }


    public ImageMeta findById(Long imageId) throws Exception {
        return imageRepository.findById(imageId).orElseThrow(() -> new Exception("No image with" + " " + imageId));
    }

    public String save(MultipartFile request) {

        log.info("name " + request.getOriginalFilename());
        log.info("size " + request.getSize());

        if (!request.isEmpty()) {
            if (request.getSize() <= maxSize) {
                ImageMeta image = null;
                try {
                    PrivateKey privateKey = SignService.getPrivateKey(privateKeyFileName);
                    image = ImageMeta.builder()
                            .name(request.getOriginalFilename())
                            .mimeType(request.getContentType())
                            .size(request.getSize())
                            .data(request.getBytes())
                            .digitalSign(SignService.sign(request.getBytes(), privateKey))
                            .build();

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imageRepository.save(image);

                return "ok";

            } else return "Image size is above 2 mb";
        }

        return "no image";
    }


    public ResponseEntity<?> delete(Long imageId) throws Exception {
            ImageMeta image = imageRepository.findById(imageId).orElseThrow(() -> new Exception("Not found image with" + " " + imageId));

            imageRepository.delete(image);

            return ResponseEntity.ok("Image deleted successfully with id" + image);
        }


    public ResponseEntity<?> deleteAll() throws Exception {
        imageRepository.deleteAll();

        return ResponseEntity.ok("All images deleted");
    }




}
