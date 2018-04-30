package com.example.office365wopi.request;

public enum WopiRequestHeader {
    REQUEST_TYPE("X-WOPI-Override"),

    LOCK("X-WOPI-Lock"),
    OLD_LOCK("X-WOPI-OldLock"),
    LOCK_FAILURE_REASON("X-WOPI-LockFailureReason"),

    SUGGESTED_TARGET("X-WOPI-SuggestedTarget"),
    RELATIVE_TARGET("X-WOPI-RelativeTarget"),
    OVERWRITE_RELATIVE_TARGET("X-WOPI-OverwriteRelativeTarget");

    private String name;

    WopiRequestHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
