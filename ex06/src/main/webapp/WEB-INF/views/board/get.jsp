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
                Board Read
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="form-group">
                    <label>Bno</label>
                    <input class="form-control" name='bno' value='<c:out value="${board.bno}" />' readonly="readonly">
                </div>

                <div class="form-group">
                    <label>Title</label>
                    <input class="form-control" name='title' value='<c:out value="${board.title}" />' readonly="readonly">
                </div>

                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}" /></textarea>
                </div>

                <div class="form-group">
                    <label>Writer</label>
                    <input class="form-control" name='writer' value='<c:out value="${board.writer}" />' readonly="readonly">
                </div>

                <button data-oper='modify' class="btn btn-default" <!--onclick="location.href='/board/modify?bno=<c:out value="${board.bno}" />'"-->Modify</button>
                <button data-oper='list' class="btn btn-info" <!--onclick="location.href='/board/list'"-->List</button>

                <form  id="operForm" action="/board/modify" method="get">
                    <input type="hidden" id="bno" name='bno' value='<c:out value="${board.bno}" />'>
                    <input type="hidden" id="pageNum" name='pageNum' value='<c:out value="${cri.pageNum}" />'>
                    <input type="hidden" id="amount" name='amount' value='<c:out value="${cri.amount}" />'>
                    <input type="hidden" id="type" name='type' value='<c:out value="${cri.type}" />'>
                    <input type="hidden" id="keyword" name='keyword' value='<c:out value="${cri.keyword}" />'>
                </form>

            </div>
            <!--  end panel-body -->
         </div>
        <!-- end panel -->
    </div>
</div>
<!-- /.row -->

<div class='row'>
    <div class="col-lg-12">
        <!-- /.panel -->
        <div class="panel panel-default">
            <!--<div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i> Reply
            </div> -->
            <div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i> Reply
                <button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <ul class="chat">

                </ul>
                <!-- ./ end ul -->
            </div>
            <!-- /.panel .chat-panel -->
            <div class="panel-footer"></div>
        </div>
    </div>
    <!-- ./ end row -->
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                  <label>Reply</label>
                  <input class="form-control" name='reply' value='New Reply!!!!'>
                </div>
                <div class="form-group">
                  <label>Replyer</label>
                  <input class="form-control" name='replyer' value='replyer'>
                </div>
                <div class="form-group">
                  <label>Reply Date</label>
                  <input class="form-control" name='replyDate' value=''>
                </div>
            </div>
            <!-- /.modal-content -->
            <div class="modal-footer">
               <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
               <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
               <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
               <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
            </div>
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
</div>

<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>
    $(function() {
        var bnoValue = '<c:out value="${board.bno}" />';
        var replyUl = $(".chat");

        showList(1);
        function showList(page) {
            replyService.getList(
                {bno:bnoValue,page:page || 1},
                function(list){
                    var str = "";
                    if(list == null || list.length == 0) {
                        return;
                    }

                    for(var i=0,len=list.length||0; i<len; i++) {
                        str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
                        str +="  <div><div class='header'><strong class='primary-font'>["+list[i].rno+"] "+list[i].replyer+"</strong>";
                        str +="    <small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
                        str +="    <p>"+list[i].reply+"</p></div></li>";

                    }
                    replyUl.html(str);
                }
            );
        };

        var modal = $(".modal");
        var modalInputReply = modal.find("input[name='reply']");
        var modalInputReplyer = modal.find("input[name='replyer']");
        var modalInputReplyDate = modal.find("input[name='replyDate']");

        var modalModBtn = $("#modalModBtn");
        var modalRemoveBtn = $("#modalRemoveBtn");
        var modalRegisterBtn = $("#modalRegisterBtn");

        $("#addReplyBtn").on("click", function(){
            modal.find("input").val("");
            modalInputReplyDate.closest("div").hide();
            modal.find("button[id !='modalCloseBtn']").hide();

            modalRegisterBtn.show();

            modal.modal("show");
        });

        modalRegisterBtn.on("click", function(){
            var reply = {bno:bnoValue,reply:modalInputReply.val(),replyer:modalInputReplyer.val()};
            replyService.add(
                reply,
                function(result){
                    modal.find("input").val();
                    modal.modal("hide");
                    showList(1);
                }
            );
        });

        $("#modalCloseBtn").on("click", function(){
            $(".modal").modal("hide");
        });

        // 댓글 상세 조회
        $(".chat").on("click","li", function(){
            var rno = $(this).data("rno");

            replyService.get(
                rno,
                function(reply){
                    modalInputReply.val(reply.reply);
                    modalInputReplyer.val(reply.replyer).attr("readonly", "readonly");;
                    modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
                    modal.data("rno", reply.rno);

                    modal.find("button[id !='modalCloseBtn']").hide();
                    modalModBtn.show();
                    modalRemoveBtn.show();

                    modal.modal("show");
                }
            );
        });

        // 댓글 수정
        modalModBtn.on("click", function(){
            var reply = {rno:modal.data("rno"), reply:modalInputReply.val()};

            replyService.update(reply,
                function(result){
                    alert(result);
                    modal.modal("hide");
                    showList(1);
            });
        });

        // 댓글 삭제
        modalRemoveBtn.on("click", function(){
            var rno = modal.data("rno");

            replyService.remove(
                rno,
                function(result){
                    alert(result);
                    modal.modal("hide");
                    showList(1);
            });
        });
    });

</script>

<script>
/*
    $(function() {
        console.log("replyService");

        var bnoValue = '<c:out value="${board.bno}" />';
        replyService.add(
            {bno:bnoValue,reply:"test",replyer:"JS Test"},
            function(result){
                alert("result : " + result);
            }
        );

        replyService.getList(
            {bno:bnoValue,page:1},
            function(list){
                for(var i=0, len=list.length||0; i<len; i++) {
                    console.log(list[i]);
                }
            }
        );

        replyService.remove(
            28,
            function(count) {
                if(count == "success") {
                    alert("remove");
                }
            },
            function(err) {
                alert("Error");
            }
        );

        replyService.update(
            {rno:30,bno:bnoValue,reply:"니가 그러고도 사람이야?"},
            function(up){
                if(up == "success") {
                    alert("update");
                }
            },
            function(err) {
                alert("Error");
            }
        );

        replyService.get(
            30,
            function(data){
                console.log(data);
            },
            function(err) {
                alert("Error");
            }
        );
    });
*/
</script>


<script>
    $(function() {
        var operForm = $("#operForm");

        $("button[data-oper='modify']").on("click", function(e){
            operForm.attr("action", "/board/modify").submit();
        });
        $("button[data-oper='list']").on("click", function(e){
            operForm.find("#bno").remove();
            operForm.attr("action", "/board/list").submit();
        });
    });
</script>
<%@include file="../includes/footer.jsp"%>