package com.example.office365wopi.service;

import com.example.office365wopi.response.CheckFileInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class WopiProtocalService {

    @Value("${localstorage.path}")
    private String filePath;

    public ResponseEntity<Resource> handleGetFileRequest(String name) throws UnsupportedEncodingException, FileNotFoundException {
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
     * @TODO: rework on it based on the description of document
     */
    public void handlePutFileRequest(String name, byte[] content) throws IOException {
        Path path = Paths.get(filePath + name);
        Files.write(path, content);
    }

    public ResponseEntity<CheckFileInfoResponse> handleCheckFileInfoRequest(String name) throws UnsupportedEncodingException {
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
            }
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)).body(info);
    }
}
