const password = document.getElementById('user_password');
const password2 = document.getElementById('user_password2');
const phoneA = document.getElementById('phone').parentElement;
const phone2 = document.getElementById('phone2').parentElement;
password.addEventListener("change", checkPassword);
password2.addEventListener("change", checkPassword2);

// 에러메세지
const setError = (element, message,) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');
    errorDisplay.innerText = message;
}
//성공시 에러메시지 삭제
const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');
    errorDisplay.innerText = '';
}

function modify() {
    checkPassword();
    checkPassword2();
    if (confirm("회원정보를 수정하시곘습니까?")) {
        if (checkPassword() === true && checkPassword2() === true) {
            $("form").submit();
        } else {
            alert("정보수정실패");
        }
    }
}

function checkPassword() {
    const passwordValue = password.value.trim();
    var pwPattern = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

    if (passwordValue == "") {
        return true;
    } else if (!pwPattern.test(passwordValue)) {
        setError(password, "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
        return false;
    } else {
        setSuccess(password);
    }
    return true;
}

function checkPassword2() {
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();

    if (passwordValue == "" && password2Value == "") {
        return true;
    } else if (password2Value !== passwordValue) {
        setError(password2, "비밀번호가 일치하지 않습니다.");
        return false;
    } else {
        setSuccess(password2);
    }
    return true;
}


$('#phoneChk2').click(function (e) {
    const phone2Value = document.getElementById("phone2").value.trim();
    if (phone2Value === "") {
        setError(phone2, "인증번호를 입력해 주세요.");
        return false;
    } else if (phone2Value !== code2) {
        setError(phone2, "인증번호가 일치하지 않습니다.")
        return false;
    } else {
        setSuccess(phone2);
        $('.phonemodibtn').attr("disabled", false);
    }
    return true;
});


//휴대폰인증 후 부모창으로 데이터전송하는 함수
$('.phonemodibtn').click(function (e) {
    e.preventDefault();
    $('#phoneModal').modal("hide");
    document.getElementById("originphone").value = document.getElementById("phone").value
    $("#phone").value = "";
    $('#phone2').value = "";
})

// //후대폰 문자보내기
var code2 = "";
$("#phoneChk").click(function () {
    const phoneValue = document.getElementById("phone").value.trim();
    if (phoneValue === "") {
        setError(phoneA, "휴대폰 번호를 입력해 주세요.")
    } else {
        setSuccess(phoneA);
        alert("인증번호 발송이 완료되었습니다.\n휴대폰에서 인증번호 확인을 해주십시오.");
        var phone = $("#phone").val();
        $.ajax({
            type: "GET",
            url: "phoneCheck?user_phone_number=" + phone,
            success: function (data) {
                if (data == "error") {

                } else {
                    console.log(data);
                    $("#phone2").attr("disabled", false);
                    $("#phoneChk2").css("display", "inline-block");
                    $(".successPhoneChk").text("인증번호를 입력한 뒤 본인인증을 눌러주십시오.");
                    $(".successPhoneChk").css("color", "green");
                    code2 = data;
                }
            }
        });
    }
});
// 휴대폰 인증번호 대조
$("#phoneChk2").click(function () {
    console.log("클릭했음");
    if ($("#phone2").val() == code2) {
       setError(phone2, "인증번호가 일치합니다.")
    } else {
         setError(phone2, "인증번호가 일치하지 않습니다.");
    }
});


