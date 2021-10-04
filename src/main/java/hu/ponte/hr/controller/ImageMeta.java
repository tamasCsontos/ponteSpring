package hu.ponte.hr.controller;

import lombok.*;

import javax.persistence.*;

/**
 * @author zoltan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class ImageMeta
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String name;
	private String mimeType;
	private long size;
	private String digitalSign;

	@Lob
	private byte[] data;
}

