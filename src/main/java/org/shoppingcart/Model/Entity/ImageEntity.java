package org.shoppingcart.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.sql.Blob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "filename")
    private String filename;
    @Column(name = "filetype")
    private String filetype;
    @Lob
    @Column(name = "image")
    private Blob image; // Lưu các hình ảnh hệ nhị phân
    @Column(name = "downloadurl")
    private String downloadURL;
    @ManyToOne
    @JoinColumn(name = "productid", referencedColumnName = "id")
    @Cascade(value = {CascadeType.MERGE})
    @JsonBackReference
    private ProductEntity product;
}
