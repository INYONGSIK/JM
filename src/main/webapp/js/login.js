//모달을 전역변수로 선언
var modalContents = $(".modal-contents");
var modal = $("#defaultModal");
var checked = false;
var checked2  = false;
const email = document.getElementById("user_email");
const password = document.getElementById("user_password");
function checkAll(){
        checkEmail()
        checkPassword();
        $("#form").submit();
}

function checkEmail(){
    if(email.value == ""){
          modalContents.text("이메일을 입력하여 주시기 바랍니다.");
          modal.modal("show");
          $("#user_email").focus();
          return false;
          break;
    }else{
        $.ajax({
            type: "POST",
            url: "/emailoverlap",
            data: {
                "user_email": $("#user_email").val()
            },
            success: function(data){
                var isOk = data
                if(!isOk){
                    modalContents.text("이메일이 존재하지 않습니다.");
                    modal.modal("show");
                    return  false;
                    break;
                }else{
                    return  true;
                    console.log("checked : " +checked)
                }
            }
        })
    }
    return true;
}

function checkPassword(){
    if(password.value == ""){
        modalContents.text("패스워드를 입력하여 주시기 바랍니다.");
        modal.modal("show");
        $("#user_password").focus();
        return  false;
        break;
    }else{
        $.ajax({
             type: "POST",
             url: "/valid",
             data: {
                "user_email": $("#user_email").val(),
                 "user_password" : $("#user_password").val(),
              },
              success:function(data){
              if( data == "검증실패"){
                  modalContents.text("비밀번호가 틀렸습니다.");
                  modal.modal("show");
                  $("#user_password").focus();
                  return  false;
                  break;
              }else{
              console.log("checked2 : "+checked2)
                return  true;
              }
           }
        })
    }
    return true;
}


