package com.example.office365wopi.service;

import com.example.office365wopi.response.CheckFileInfoResponse;
import com.example.office365wopi.validator.WopiAuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class WopiProtocalService {

    @Value("${localstorage.path}")
    private String filePath;

    private WopiAuthenticationValidator validator;

    @Autowired
    public WopiProtocalService(WopiAuthenticationValidator validator) {
        this.validator = validator;
    }

    public ResponseEntity<Resource> handleGetFileRequest(String name, HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException {
        this.validator.validate(request);
        String path = filePath + name;
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" +
                new String(file.getName().getBytes("utf-8"), "ISO-8859-1"));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    /**
     * @param name
     * @param content
     * @param request
     * @TODO: rework on it based on the description of document
     */
    public void handlePutFileRequest(String name, byte[] content, HttpServletRequest request) throws IOException {
        this.validator.validate(request);
        Path path = Paths.get(filePath + name);
        Files.write(path, content);
    }

    public ResponseEntity<CheckFileInfoResponse> handleCheckFileInfoRequest(String name, HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException {
        this.validator.validate(request);
        CheckFileInfoResponse info = new CheckFileInfoResponse();
        String fileName = URLDecoder.decode(name, "UTF-8");
        if (fileName != null && fileName.length() > 0) {
            File file = new File(filePath + fileName);
            if (file.exists()) {
                info.setBaseFileName(file.getName());
                info.setSize(file.length());
                info.setOwnerId("admin");
                info.setVersion(file.lastModified());
                info.setAllowExternalMarketplace(true);
                info.setUserCanWrite(true);
                info.setSupportsUpdate(true);
                info.setSupportsLocks(true);
            } else {
                throw new FileNotFoundException("Resource not found/user unauthorized");
            }
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)).body(info);
    }
}
