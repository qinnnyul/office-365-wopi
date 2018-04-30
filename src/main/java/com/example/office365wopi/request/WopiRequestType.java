package com.example.office365wopi.request;

public enum WopiRequestType {
    //Files endpoint
    CHECK_FILE_INFO,
    PUT_RELATIVE_FILE,
    LOCK,
    GET_LOCK,
    REFRESH_LOCK,
    UNLOCK,
    UNLOCK_AND_RELOCK,

    //Files contents endpoints
    GET_FILE,
    PUT_FILE
}
