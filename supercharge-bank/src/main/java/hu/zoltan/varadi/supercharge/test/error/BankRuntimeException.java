package hu.zoltan.varadi.supercharge.test.error;

public class BankRuntimeException extends RuntimeException {

    public BankRuntimeException() {
    }

    public BankRuntimeException(String message) {
        super(message);
    }

    public BankRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankRuntimeException(Throwable cause) {
        super(cause);
    }

    public BankRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
