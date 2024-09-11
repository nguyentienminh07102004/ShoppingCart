package org.shoppingcart.Exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message) {
        super(message);
    }

    public ResourceAlreadyExists() {
        super("Resource is exists!");
    }
}
