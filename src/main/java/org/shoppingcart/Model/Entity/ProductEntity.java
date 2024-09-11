package org.shoppingcart.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "brand")
    private String brand;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "inventory")
    private Integer inventory; // số lượng tồn kho

    @ManyToOne
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    @JsonManagedReference
    private CategoryEntity category;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ImageEntity.class,
            mappedBy = "product", orphanRemoval = true)
    @Cascade(value = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private List<ImageEntity> images;

    @OneToMany(targetEntity = CartItemEntity.class,
            orphanRemoval = true, mappedBy = "product")
    @Cascade(value = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<CartItemEntity> cartItems;
}
