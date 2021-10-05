package hu.ponte.hr.repository;


import hu.ponte.hr.controller.ImageMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade=CascadeType.PERSIST)
    private ImageMetaJpaEntity imageMetaJpaEntity;

    @Lob
    private byte[] data;
}
