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
</head>
<body>
    <h2>Upload Ajax</h2>
    <div class="uploadDiv">
        <input type="file" name="uploadFile" multiple>
    </div>

    <button id="uploadBtn">Upload</button>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
    integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
    crossorigin="anonymous"></script>
</body>
<script>
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
    </script>
</html>