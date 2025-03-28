console.log("Reply Module");

var replyService = (function(){

    function add(reply, callback, error) {
        $.ajax({
            url : '/replies/new',
            type : 'post',
            data : JSON.stringify(reply),
            dataType : "text", // 서버에서 받는 데이터 타입 지정, 생략할 경우 자동으로 타입 처리
            contentType : "application/json; charset=UTF-8", // 서버로 보내는 데이터 타입 지정
            success : function(result, status, xhr) {
                if(callback) {
                    callback(result);
                }
            },
            error : function(xhr, status, er){ // try-catch 같은 느낌쓰
                if(error) {
                    error(er);
                }
            }
        });
    }

    function getList(param, callback, error) {

        var bno = param.bno;
        var page = param.page || 1;

        $.getJSON(
            "/replies/pages/" + bno + "/" + page,
            function(data){
                if(callback) {
                    callback(data);
                }
            }
        ).fail(
            function(xhr, status, er){
                if(error) {
                    error(er);
                }
            }
        );
    }

    function remove(rno, callback, error) {
        $.ajax({
            url : '/replies/' + rno,
            type : 'delete',
            success : function(result, status, xhr) {
                if(callback) {
                    callback(result);
                }
            },
            error : function(xhr, status, er){
                if(error) {
                    error(er);
                }
            }
        });
    }

    function update(reply, callback, error) {
        $.ajax({
            url : '/replies/' + reply.rno,
            type : 'put',
            data : JSON.stringify(reply),
            dataType : "text",
            contentType : "application/json; charset=UTF-8",
            success : function(result, status, xhr) {
                if(callback) {
                    callback(result);
                }
            },
            error : function(xhr, status, er){
                if(error) {
                    error(er);
                }
            }
        });
    }

    function get(rno, callback, error) {
        $.get(
            "/replies/" + rno,
            function(data){
                if(callback) {
                    callback(data);
                }
            }
        ).fail(
            function(xhr, status, er){
                if(error) {
                    error(er);
                }
            }
        );
    }

    function displayTime(timeValue) {

        var today = new Date();

        var gap = today.getTime() - timeValue;

        var dateObj = new Date(timeValue);
        var str = "";

        if (gap < (1000 * 60 * 60 * 24)) {

            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();

            return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss ].join('');

        } else {
            var yy = dateObj.getFullYear();
            var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
            var dd = dateObj.getDate();

            return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd ].join('');
        }
    }

    return {add:add, getList:getList, remove:remove, update:update, get:get, displayTime:displayTime};
    // 호출 방식 - replyService.add();
})();