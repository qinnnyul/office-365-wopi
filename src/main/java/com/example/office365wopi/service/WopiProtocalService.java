package com.example.office365wopi.service;

import com.example.office365wopi.response.CheckFileInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@Service
public class WopiProtocalService {

    @Value("${localstorage.path}")
    private String filePath;

    public void handleGetFileRequest(String name, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String path = filePath + name;
            File file = new File(path);
            String filename = file.getName();
            inputStream = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            response.reset();

            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(filename.getBytes("utf-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param name
     * @param content
     * @TODO: rework on it based on the description of document
     */
    public void handlePutFileRequest(String name, byte[] content) {
        String path = filePath + name;
        File file = new File(path);
        FileOutputStream fop = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fop = new FileOutputStream(file);
            fop.write(content);
            fop.flush();
            System.out.println("------------ save file ------------ ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fop.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCheckFileInfoRequest(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        CheckFileInfoResponse info = new CheckFileInfoResponse();
        try {
            // for Chinese encode
            String fileName = URLDecoder.decode(uri.substring(uri.indexOf("wopi/files/") + 11, uri.length()), "UTF-8");
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

            ObjectMapper mapper = new ObjectMapper();
            String Json = mapper.writeValueAsString(info);

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(Json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
