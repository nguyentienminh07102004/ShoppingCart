package org.shoppingcart.Model.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "totalamount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = CartItemEntity.class,
            orphanRemoval = true, mappedBy = "cart")
    @Cascade(value = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JsonManagedReference
    private List<CartItemEntity> cartItems;

    public BigDecimal updatePrice() {
        return cartItems.stream()
                .map(item -> {
                    BigDecimal unitPrice = item.getUnitPrice();
                    if(unitPrice == null) unitPrice = BigDecimal.ZERO;
                    return unitPrice.multiply(new BigDecimal(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(CartItemEntity item) {
        cartItems.removeIf((cartItem) -> cartItem.getId().equals(item.getId()));
        cartItems.add(item);
        item.setCart(this);
        BigDecimal totalAmount = updatePrice();
        this.setTotalAmount(totalAmount);
    }

    public void removeItem(CartItemEntity cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        BigDecimal totalAmount = updatePrice();
        this.setTotalAmount(totalAmount);
    }
}
