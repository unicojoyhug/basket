package io.unicojoyhug.basket.errors;

public class NegativeQuantityException extends RuntimeException{
    public NegativeQuantityException(String ex){
        super(ex);
    }
}
