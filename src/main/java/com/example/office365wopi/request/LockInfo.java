package com.example.office365wopi.request;

import java.util.Calendar;

public class LockInfo {
    private String lock;
    private Calendar dateCreated;
    private boolean expired;

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isExpired() {
        this.getDateCreated().add(Calendar.MINUTE, 5);
        return Calendar.getInstance().after(this.getDateCreated());
    }

}
