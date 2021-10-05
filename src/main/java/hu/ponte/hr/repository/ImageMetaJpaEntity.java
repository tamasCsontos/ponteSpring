package hu.ponte.hr.repository;

import lombok.*;

import javax.persistence.*;



@Entity
@Table(name = "imageMeta")
@Data
@Builder
public class ImageMetaJpaEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String id;

        private String name;
        private String mimeType;
        private long size;

        @Column(columnDefinition = "LONGTEXT")
        private String digitalSign;


}
