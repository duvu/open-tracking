package me.duvu.tracking.exception;

/**
 * Created by admin on 7/5/2016.
 */
public class BusinessLogicException extends RuntimeException {

    private static final long serialVersionUID = 6728691198819448190L;

    public BusinessLogicException() {
        super("Business Logic Exception");
    }
}
