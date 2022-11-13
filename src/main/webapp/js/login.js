$(function() {
            var modalContents = $(".modal-contents");
            var modal = $("#defaultModal");
            $('#submitBtn').click(function() {
                var userId  = $('#userId' ).val() ;
                var userPwd = $('#userPwd').val() ;

            if(userId == "" || userPwd == ""){
                modalContents.text("이메일과 비밀번호를 입력하여 주시기 바랍니다.");
                modal.modal("show");
                return false;
            }
            $.ajax({
                type : "POST",
                url: '/valid',
                data: {
                "user_email": userId,
                 "user_password" : userPwd,
              },
                success: function(data) {
                    if(data == "검증실패"){
                     modalContents.text("아이디와 비밀번호를 확인해 주세요.");
                     modal.modal("show");
                }
                    else{
                        $("#loginForm").submit();
                       }
                }
            });
            }) ;
        }) ;