package com.example.office365wopi.rest;

import com.example.office365wopi.response.CheckFileInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class WopiProtocalService {

    @Value("${localstorage.path}")
    private String filePath;

    public void handleGetFileRequest(String name, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 文件的路径
            String path = filePath + name;
            File file = new File(path);
            // 取得文件名
            String filename = file.getName();
            // 以流的形式下载文件
            inputStream = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            // 清空response
            response.reset();

            // 设置response的Header
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

    public void handleCheckFileInfoRequest(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        CheckFileInfoResponse info = new CheckFileInfoResponse();
        try {
            // 获取文件名, 防止中文文件名乱码
            String fileName = URLDecoder.decode(uri.substring(uri.indexOf("wopi/files/") + 11, uri.length()), "UTF-8");
            if (fileName != null && fileName.length() > 0) {
                File file = new File(filePath + fileName);
                if (file.exists()) {
                    // 取得文件名
                    info.setBaseFileName(file.getName());
                    info.setSize(file.length());
                    info.setOwnerId("admin");
                    info.setVersion(file.lastModified());
                    info.setSha256(getHash256(file));
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的SHA-256值
     *
     * @param file
     * @return
     */
    private String getHash256(File file) {
        String value = "";
        // 获取hash值
        try {
            byte[] buffer = new byte[1024];
            int numRead;
            InputStream fis = new FileInputStream(file);
            //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest complete = MessageDigest.getInstance("SHA-256");
            do {
                //从文件读到buffer
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    //用读到的字节进行MD5的计算，第二个参数是偏移量
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();
            value = new String(Base64.encodeBase64(complete.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
