package com.example.office365wopi.rest;

import com.example.office365wopi.service.WopiProtocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/wopi")
public class WopiProtocalController {

    private WopiProtocalService wopiProtocalService;

    @Autowired
    public WopiProtocalController(WopiProtocalService wopiProtocalService) {
        this.wopiProtocalService = wopiProtocalService;
    }

    @GetMapping("/files/{name}/contents")
    public void getFile(@PathVariable(name = "name") String name, HttpServletResponse response) {
        wopiProtocalService.handleGetFileRequest(name, response);
    }

    @PostMapping("/files/{name}/contents")
    public void putFile(@PathVariable(name = "name") String name, @RequestBody byte[] content) {
        wopiProtocalService.handlePutFileRequest(name, content);
    }


    @GetMapping("/files/{name}")
    public void getFileInfo(HttpServletRequest request, HttpServletResponse response) {
        wopiProtocalService.handleCheckFileInfoRequest(request, response);
    }


}
