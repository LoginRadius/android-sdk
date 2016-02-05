package com.loginradius.userregistration;

public class RegistrationException extends RuntimeException {
    public RegistrationException(Throwable throwable) {
        super(throwable);
    }

    public RegistrationException() {
        super();
    }

    public RegistrationException(String detailMessage) {
        super(detailMessage);
    }

    public RegistrationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
