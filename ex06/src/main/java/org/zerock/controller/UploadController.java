package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@Log4j
public class UploadController {

    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("upload form");

    }
//
//    @PostMapping("/uploadFormAction")
//    public void uploadFormAction(MultipartFile[] uploadFile, Model model) {
//        String uploadFolder = "c:\\upload";
//
//        for(MultipartFile multipartFile : uploadFile) {
//            // getOriginalFilename() - 실제 파일 이름, getSize() - 파일 크기, transferTo() - 파일 전송
//            log.info("=========");
//            log.info("getOriginalFilename : " + multipartFile.getOriginalFilename());
//            log.info("getSize : " + multipartFile.getSize());
//
//            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
//
//            try {
//                multipartFile.transferTo(saveFile);
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
        log.info("upload ajax");
    }

    @PostMapping("/uploadAjaxAction")
    public void uploadAjaxAction(MultipartFile[] uploadFile) {

        String uploadFolder = "c:\\upload";

        for(MultipartFile multipartFile : uploadFile) {
            log.info("=========");
            log.info("getOriginalFilename : " + multipartFile.getOriginalFilename());
            log.info("getSize : " + multipartFile.getSize());

            String uploadFilename = multipartFile.getOriginalFilename();

            File saveFile = new File(uploadFolder, uploadFilename);

            try {
                multipartFile.transferTo(saveFile);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }


    }
}