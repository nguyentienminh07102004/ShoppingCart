package org.shoppingcart.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cartitems")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "totalprice")
    private BigDecimal totalPrice;
    @Column(name = "unitprice")
    private BigDecimal unitPrice;
    @ManyToOne(targetEntity = CartEntity.class)
    @JoinColumn(name = "cartid", referencedColumnName = "id")
    @Cascade(value = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private CartEntity cart;
    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "productid", referencedColumnName = "id")
    @JsonManagedReference
    private ProductEntity product; // theo ý hiểu là có nhiều sản phẩm ví dụ mua 20 cái tv thì có nhiều item thuộc cùng sản phẩm
    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(this.quantity));
    }
}
