package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller // @Controller + @ResponseBody == @RestController
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

    @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[] uploadFile) {

        String uploadFolder = "c:\\upload";
        String uploadFolderPath = getFolder();

        File uploadPath = new File(uploadFolder, uploadFolderPath); // 폴더 이름, 폴더 경로

        List<AttachFileDTO> list = new ArrayList<>();

        if(uploadPath.exists() == false) {
            // 경로에 폴더가 존재하지 않는다면
            uploadPath.mkdirs(); // 폴더 생성
        }

        for(MultipartFile multipartFile : uploadFile) {

            AttachFileDTO attachDTO = new AttachFileDTO();

            log.info("=========");
            log.info("getOriginalFilename : " + multipartFile.getOriginalFilename());
            log.info("getSize : " + multipartFile.getSize());

            String uploadFilename = multipartFile.getOriginalFilename();
            attachDTO.setFileName(uploadFilename);

            UUID uuid = UUID.randomUUID(); // 고유 번호 부여
            uploadFilename = uuid.toString() + "_" + uploadFilename;

            try {
                byte[] fileBytes = multipartFile.getBytes();

//                File saveFile = new File(uploadFolder, uploadFilename);
                File saveFile = new File(uploadPath, uploadFilename);

                multipartFile.transferTo(saveFile);

                attachDTO.setUuid(uuid.toString());
                attachDTO.setUploadPath(uploadFolderPath);

                if(checkImagetype(saveFile)) {
                    attachDTO.setImage(true);
                    File thumnailFile = new File(uploadPath, "s_" + uploadFilename);
                    FileOutputStream thumnail = new FileOutputStream(thumnailFile);

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
                    Thumbnailator.createThumbnail(inputStream, thumnail, 100, 100);

                    inputStream.close();
                    thumnail.close();
                }

                list.add(attachDTO);

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);

        return str.replace("-", File.separator);
    }

    private boolean checkImagetype(File file) {
        try{
            String contentType = Files.probeContentType(file.toPath()); // 해당 파일의 패스 정보(컨텐츠 타입)를 넘겨줌
            // 이미지 파일이라면 "image.." 로 시작함
           return  contentType.startsWith("image");
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {  // 전달되는 데이터 : 2025\03\31\파일이름.jpg
        File file = new File("C:\\upload\\" + fileName);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Contet-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK); //(데이터 정보, 헤더 정보, 상태값);
            log.info(result);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName) {

        log.info("fileName : " + fileName);

        Resource resource = new FileSystemResource("C:\\upload\\" + fileName);

        log.info("resource : " + resource);

        String resourceName = resource.getFilename();

        String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

        HttpHeaders header = new HttpHeaders();

        try {
            header.add("Content-Disposition", "attachment; fileName=" + new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1"));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String>  deleteFile(String fileName, String type) {

        log.info("fileName : " + fileName);
        log.info("type : " + type);
        File file;

        try{
            file = new File("C:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

            file.delete();

            if(type.equals("image")) {
                String largeFile = file.getAbsolutePath().replace("s_", "");
                file = new File(largeFile);
                file.delete();
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}