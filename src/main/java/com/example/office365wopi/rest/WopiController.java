package com.example.office365wopi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/wopi")
public class WopiController {

    private WopiProtocalService wopiProtocalService;

    @Autowired
    public WopiController(WopiProtocalService wopiProtocalService) {
        this.wopiProtocalService = wopiProtocalService;
    }

    @GetMapping("/files/{name}/contents")
    public void getFile(@PathVariable(name = "name") String name, HttpServletResponse response) {
        wopiProtocalService.handleGetFileRequest(name, response);
    }

    @GetMapping("/files/{name}")
    public void getFileInfo(HttpServletRequest request, HttpServletResponse response) {
        wopiProtocalService.handleCheckFileInfoRequest(request, response);
    }


}
