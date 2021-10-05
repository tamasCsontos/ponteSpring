package hu.ponte.hr.repository;


import hu.ponte.hr.controller.ImageMeta;
import lombok.Builder;
import lombok.Data;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;

@Entity
@Data
@Table(name = "images")
@Builder
public class ImageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne(cascade=CascadeType.PERSIST)
    private ImageMetaJpaEntity imageMetaJpaEntity;

    @Lob
    private byte[] data;
}
