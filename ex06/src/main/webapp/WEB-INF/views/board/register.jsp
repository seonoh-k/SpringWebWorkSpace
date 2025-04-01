<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../includes/header.jsp"%>

<div class="row">
     <div class="col-lg-12">
         <h1 class="page-header">Tables</h1>
     </div>
     <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Board Register
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <form role="form" action="/board/register" method="post">
                    <div class="form-group">
                        <label>Title</label> <input class="form-control" name='title'>
                    </di
                    <div class="form-group">
                        <label>Text area</label>
                        <textarea class="form-control" rows="3" name='content'></textarea>
                    </di
                    <div class="form-group">
                        <label>Writer</label> <input class="form-control" name='writer'>
                    </div>
                    <button type="submit" class="btn btn-default">Submit
                        Button</button>
                    <button type="reset" class="btn btn-default">Reset Button</button>
                </form>
            </div>
            <!--  end panel-body -->
         </div>
        <!-- end panel -->
    </div>
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-heading">File Attach</div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="form-group uploadDiv">
                    <input type="file" name='uploadFile' multiple>
                </div>

                <div class='uploadResult'>
                    <ul>

                    </ul>
                </div>
            </div>
          <!--  end panel-body -->
        </div>
      <!--  end panel-body -->
    </div>
    <!-- end panel -->
</div>
<!-- /.row -->

<script>
    $(function(){
        var formObj = $("form[role='form']");
        $("button[type='submit']").on("click", function(e){
            e.preventDefault();

            var str = "";

            console.log("submit changed");

            $(".uploadResult ul li").each(function(i, obj) {

                var jobj = $(obj);

                str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
                str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
                str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
                str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ jobj.data("type")+"'>";
            });

            formObj.append(str);
            formObj.submit();
        });

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

        $("input[type='file']").change(function(){
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
                    console.log(result);
                    showUploadResult(result);
                }
            });
        });

        function showUploadResult(uploadResultArr){

            if(!uploadResultArr || uploadResultArr.length == 0) {
                return;
            }

            var uploadUL = $(".uploadResult ul");
            var str = "";

            $(uploadResultArr).each(function(i, obj){
                if(obj.image) {
                /*
                    var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);

                    str += "<li><div>";
                    str += "<span>" + obj.fileName + "</span>";
                    str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/display?fileName=" + fileCallPath+"'>";
                    str += "</div></li>";
                */
                    var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);

                    str += "<li data-path='" + obj.uploadPath + "'";
                    str += "data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>"
                    str += "<div>";
                    str += "<span> " + obj.fileName + "</span>";
                    str += "<button type='button' data-file=\'" + fileCallPath + "\' "
                    str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/display?fileName=" + fileCallPath + "'>";
                    str += "</div></li>";

                }else {
                /*
                    var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
                    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                    str += "<li><div>";
                    str += "<span> "+ obj.fileName + "</span>";
                    str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/resources/img/imgimg.png'></a>";
                    str += "</div></li>";
                */
                    var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
                    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                    str += "<li "
                    str += "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "' ><div>";
                    str += "<span>" + obj.fileName + "</span>";
                    str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' "
                    str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/resources/img/imgimg.png'>";
                    str += "</div></li>";
                }
            });

            uploadUL.append(str);
        }

        $(".uploadResult").on("click", "button", function(){
            var targetFile = $(this).data("file"); // 파일 경로
            var targetType = $(this).data("type"); // 파일 속성

            var targetLi = $(this).closest("li");

            $.ajax({
                url : "/deleteFile",
                type : "post",
                data : {fileName:targetFile, type:targetType},
                dataType : "text",
                success : function(result) {
                    alert(result);
                    targetLi.remove();
                }
            });
        });
    });
</script>

<%@include file="../includes/footer.jsp"%>