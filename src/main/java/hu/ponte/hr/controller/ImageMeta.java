package hu.ponte.hr.controller;

import lombok.*;


/**
 * @author zoltan
 */

@Data
@Builder
public class ImageMeta
{
	private long id;

	private String name;
	private String mimeType;
	private long size;

	private String digitalSign;

}

