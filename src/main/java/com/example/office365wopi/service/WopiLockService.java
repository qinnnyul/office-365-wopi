package com.example.office365wopi.service;

import com.example.office365wopi.exception.LockMismatchException;
import com.example.office365wopi.request.LockInfo;
import com.example.office365wopi.request.WopiRequestHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WopiLockService {

    private Map<String, LockInfo> locks = new ConcurrentHashMap<>();

    public ResponseEntity handleLockRequest(String name, HttpServletRequest request) {
        String newLock = request.getHeader(WopiRequestHeader.LOCK.getName());
        if (tryGetLock(name)) {
            throw new LockMismatchException(newLock, "Lock mismatch/Locked by another interface");
        } else {
            LockInfo lockInfo = new LockInfo();
            lockInfo.setLock(newLock);
            lockInfo.setDateCreated(Calendar.getInstance());
            locks.put(name, lockInfo);
        }

        return ResponseEntity.ok().build();


    }

    public ResponseEntity handleUnLockRequest(String name, HttpServletRequest request) {
        String newLock = request.getHeader(WopiRequestHeader.LOCK.getName());
        if (tryGetLock(name)) {
            LockInfo existingLock = locks.get(name);
            if (existingLock.getLock().equals(newLock)) {
                locks.remove(newLock);
                return ResponseEntity.ok().build();
            } else {
                throw new LockMismatchException(existingLock.getLock(), "Lock mismatch/Locked by another interface");
            }
        }
        throw new LockMismatchException(null, "File not locked");

    }

    public ResponseEntity handleRefreshLockRequest(String name, HttpServletRequest request) {
        String newLock = request.getHeader(WopiRequestHeader.LOCK.getName());
        if (tryGetLock(name)) {
            LockInfo existingLock = locks.get(name);
            if (existingLock.getLock().equals(newLock)) {
                //renew or refresh lock
                existingLock.setDateCreated(Calendar.getInstance());
                return ResponseEntity.ok().build();
            } else {
                throw new LockMismatchException(existingLock.getLock(), "Lock mismatch/Locked by another interface");
            }
        }
        throw new LockMismatchException(null, "File not locked");
    }

    public ResponseEntity handleUnlockAndRelockRequest(String name, HttpServletRequest request) {
        String newLock = request.getHeader(WopiRequestHeader.LOCK.getName());
        String oldLock = request.getHeader(WopiRequestHeader.OLD_LOCK.getName());

        if (tryGetLock(name)){
            LockInfo existingLock = locks.get(name);
            if (existingLock.getLock().equals(oldLock)){
                LockInfo lockInfo = new LockInfo();
                lockInfo.setLock(newLock);
                lockInfo.setDateCreated(Calendar.getInstance());
                locks.put(name, lockInfo);
            }else {
                throw new LockMismatchException(existingLock.getLock(), "Lock mismatch/Locked by another interface");
            }
        }
        throw new LockMismatchException(null, "File not locked");
    }

    private synchronized boolean tryGetLock(String name) {
        if (locks.containsKey(name)) {
            LockInfo lockInfo = locks.get(name);
            if (lockInfo.isExpired()) {
                locks.remove(name);
                return false;
            }
        }
        return true;
    }
}
