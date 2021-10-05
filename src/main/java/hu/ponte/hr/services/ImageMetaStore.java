package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.repository.ImageMetaJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import hu.ponte.hr.repository.ImageMetaJpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ImageMetaStore {

    @Autowired
    private final ImageMetaJpaRepository imageMetaJpaRepository;

    public List<ImageMeta> findAll() {
        return imageMetaJpaRepository.findAll().stream().map(imageMetaJpaEntity -> ImageMeta.builder()
                .id(String.valueOf(imageMetaJpaEntity.getId()))
                .name(imageMetaJpaEntity.getName())
                .mimeType(imageMetaJpaEntity.getMimeType())
                .size(imageMetaJpaEntity.getSize())
                .digitalSign(imageMetaJpaEntity.getDigitalSign())
                .build()).collect(Collectors.toList());
    }

}
