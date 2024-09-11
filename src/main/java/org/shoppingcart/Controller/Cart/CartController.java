package org.shoppingcart.Controller.Cart;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Model.Response.APIResponse;
import org.shoppingcart.Service.Cart.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController(value = "CartController")
@RequestMapping(value = "${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping(value = "/{cartId}")
    public ResponseEntity<APIResponse> getCartById(@PathVariable(value = "cartId") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("FOUND!", cartService.getCartById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(new APIResponse("NOT FOUND!", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/{cartId}")
    public ResponseEntity<APIResponse> clearCartById(@PathVariable(value = "cartId") String id) {
        try {
           cartService.clearCartById(id);
           return ResponseEntity.status(HttpStatus.OK)
                   .body(new APIResponse("SUCCESS!", null));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND!", exception.getMessage()));
        }
    }

    @GetMapping(value = "/total/{cartId}")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable(value = "cartId") String id) {
        try {
            BigDecimal totalAmount = cartService.getTotalPriceCartById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("SUCCESS!", totalAmount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("FAIL!", e.getMessage()));
        }
    }
    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse("CREATED SUCCESS", cartService.createCart()));
    }
}
