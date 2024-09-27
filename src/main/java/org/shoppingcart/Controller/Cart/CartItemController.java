package org.shoppingcart.Controller.Cart;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Model.Response.APIResponse;
import org.shoppingcart.Service.Cart.ICartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "CartItemController")
@RequestMapping(value = "${api.prefix}/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping (value = "/")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam(value = "cartId") String id,
                                                     @RequestParam(value = "productId") String productId,
                                                     @RequestParam(value = "quantity") Integer quantity) {
        try {
            cartItemService.addCartItemToCart(id, productId, quantity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("FAIL", e));
        }
    }

    @DeleteMapping(path = "/{cartId}/{productId}")
    public ResponseEntity<APIResponse> removeItemFromCart(@PathVariable(value = "cartId") String cartId,
                                                          @PathVariable(value = "productId") String productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND", e.getMessage()));
        }
    }

    @PutMapping(value = "/{cartId}/{productId}/")
    public ResponseEntity<APIResponse> updateItemQuantity(@PathVariable(value = "cartId") String cartId,
                                                          @PathVariable(value = "productId") String productId,
                                                          @RequestParam(value = "quantity") Integer quantity) {
        try {
            cartItemService.updateCartItemQuantity(cartId, productId, quantity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("NOT FOUND", e.getMessage()));
        }
    }
}
