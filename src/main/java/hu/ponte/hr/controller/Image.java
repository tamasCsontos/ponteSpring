package hu.ponte.hr.controller;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Image {

    private String id;

    private ImageMeta imageMeta;

    private byte[] data;

}
