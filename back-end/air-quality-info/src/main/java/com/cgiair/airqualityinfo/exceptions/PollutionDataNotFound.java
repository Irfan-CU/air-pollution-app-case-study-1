package com.cgiair.airqualityinfo.exceptions;

public class PollutionDataNotFound {

    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;

    public PollutionDataNotFound(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public PollutionDataNotFound() {
    }

    public static long getSerialVersionUID(){
        return serialVersionUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
