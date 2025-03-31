<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <style>
    .uploadResult {
       width: 100%;
       background-color: gray;
    }

    .uploadResult ul {
       display: flex;
       flex-flow: row;
       justify-content: center;
       align-items: center;
    }

    .uploadResult ul li {
       list-style: none;
       padding: 10px;
    }

    .uploadResult ul li img {
       width: 100px;
    }
    </style>

    <style>
    .bigPictureWrapper {
      position: absolute;
      display: none;
      justify-content: center;
      align-items: center;
      top:0%;
      width:100%;
      height:100%;
      background-color: gray;
      z-index: 100;
    }

    .bigPicture {
      position: relative;
      display:flex;
      justify-content: center;
      align-items: center;
    }
    </style>
</head>
<body>
    <h2>Upload Ajax</h2>
    <div class="uploadDiv">
        <input type="file" name="uploadFile" multiple>
    </div>

    <button id="uploadBtn">Upload</button>

    <div class="uploadResult">
        <ul>
        </ul>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
    integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
    crossorigin="anonymous"></script>
</body>
<script>
    $(".uploadResult").on("click", "span", function(){
        var targetFile = $(this).data("file"); // 파일 경로
        var targetType = $(this).data("type"); // 파일 속성

        $.ajax({
            url : "/deleteFile",
            type : "post",
            data : {fileName:targetFile, type:targetType},
            dataType : "text",
            success : function(result) {
                alert(result);
            }
        });
    });
/*
    $(function(){
        $("#uploadBtn").on("click", function(){
            var formData = new FormData();
            var inputFile = $("input[name='uploadFile']");
            var files = inputFile[0].files;

            console.log(files);

            for(var i=0,len=files.length; i<len; i++) {
                formData.append("uploadFile", files[i]);
            }

            $.ajax({
                url : "/uploadAjaxAction",
                processData : false,
                contentType : false,
                type : "post",
                data : formData,
                success : function(result) {
                    alert(result);
                }
            });
        });
    })
*/
    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    var maxSize = 5242880; //5MB

    function checkExtension(fileName, fileSize) {
        if(fileSize >= maxSize) {
            alert("파일 사이즈 초과");
        }

        if(regex.test(fileName)) {
            alert("해당 종류의 파일 업로드 불가");
            return false;
        }
        return true;
    }

    $("#uploadBtn").on("click", function(){
        var formData = new FormData();
        var inputFile = $("input[name='uploadFile']");
        var files = inputFile[0].files;

        for(var i=0,len=files.length; i<len; i++) {

            if(!checkExtension(files[i].name, files[i].size)) {
                return false;
            }

            formData.append("uploadFile", files[i]);
        }

        $.ajax({
            url : "/uploadAjaxAction",
            processData : false,
            contentType : false,
            type : "post",
            data : formData,
            dataType : "json",
            success : function(result) {
                //console.log(result);
                showUploadFile(result);
            }
        });

        var uploadResult = $(".uploadResult ul");

        function showUploadFile(uploadResultArr){
            var str = "";

            $(uploadResultArr).each(function(i, obj){
                if(!obj.image) {
                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
                    str += "<li><div><a href='/download?fileName=" + fileCallPath + "'><img src='/resources/img/imgimg.png'>"
                            + obj.fileName + "</a><span data-file=\'" + fileCallPath + "\' data-type='file'> x </span></div></li>";
                }else {
                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
                    str += "<li><div><img src='/display?fileName=" + fileCallPath + "'>" + obj.fileName +
                        "<span data-file=\'" + fileCallPath + "\' data-type='image'> x </span></div></li>";
                }
            });

            uploadResult.append(str);
        }
    });
    </script>
</html>