package hu.ponte.hr.controller;

import hu.ponte.hr.repository.ImageMetaJpaEntity;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Image {

    private long id;

    private ImageMeta imageMeta;

    private byte[] data;

}
