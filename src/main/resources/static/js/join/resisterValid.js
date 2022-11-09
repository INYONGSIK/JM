const form = document.getElementById('form');
const nickname = document.getElementById('user_nickname');
const email = document.getElementById('user_email');
const password = document.getElementById('user_password');
const password2 = document.getElementById('user_password2');
const name = document.getElementById('user_name');
const birthday = document.querySelector('input[type="date"]');
const phone = document.getElementById('phone').parentElement;
const phone2 = document.getElementById('phone2').parentElement;
const agree = document.getElementById("agree_all").parentElement;

nickname.addEventListener("change", checkNickname)
email.addEventListener("change", checkEmail);
password.addEventListener("change", checkPassword);
password2.addEventListener("change", checkPassword2);
name.addEventListener("change", checkName);
name.addEventListener("change", birthday);
name.addEventListener("change", user_birthday);
phone.addEventListener("change", checkPhone);
phone2.addEventListener("change", checkPhone2);

function checkAll() {
    checkNickname()
    checkEmail();
    checkPassword();
    checkPassword2();
    checkName();
    checkBirthday();
    checkPhone();
    checkPhone2();
    checkRadio();

    console.log(checkNickname());

    if (confirm("회원가입을하시겠습니까?")) {
        if (checkNickname() === true && checkEmail() === true && checkPassword() === true
            && checkPassword2() === true && checkName() === true
            && checkBirthday() === true
            && checkPhone() === true && checkPhone2() === true
            && checkRadio() === true) {
            alert("회원가입이 완료 되었습니다.감사합니다");
            $("form").submit();
        } else {
            alert("회원가입에 실패했습니다.")
            return false;
        }


    }
}

// 에러메세지
const setError = (element, message, e) => {
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

function checkNickname() {
    var checked = false;
    const nicknameValue = nickname.value.trim();
    var nicknamePattern = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{1,10}$/;
    if (nicknameValue.value === "") {
        setError(nickname, "필수 정보입니다.");
        return false;
    } else if (!nicknamePattern.test(nicknameValue)) {
        setError(nickname, "닉네임은 1~10자 한글 및 영문 입니다.");
        return false;
    } else {
       $.ajax({
       type:"POST",
       url : "/nicknameoverlap",
       data: {
            "user_nickname" : nicknameValue
       },
       success: function(data){
        var isOk = data
        if(isOk){
            console.log("false:" + nickname);
            setError(nickname, "이미있는 닉네임 입니다.");
             checked = false;
        }else{
            console.log("true"+ nickname);
            setSuccess(nickname);
            checked = true;
        }
       }
     })
    }
    return checked;
}

// 이메일 유효성 검사
function checkEmail() {
    var checked = false;
    const emailValue = email.value.trim();
    var emailPattern = /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    if (emailValue === "") {
        setError(email, "필수 정보입니다.");
        return false;
    } else if (!emailPattern.test(emailValue)) {
        setError(email, "올바른 형식으로 입력해 주세요.");
        return false;
    } else {
        // 이메일 중복 체크
        $.ajax({
            type: "POST",
            url: "/emailoverlap",
            data: {
                "user_email": emailValue
            },
            async: false,
            success: function (data) {
                var isOK = data
                if (isOK) {
                    setError(email, "이미있는 이메일 입니다.");
                    checked = false;
                } else {
                    setSuccess(email);
                    checked = true;
                }
            }
        })
    }
    return checked;

}
// 비밀번호 유혀성 검사
function checkPassword() {
    const passwordValue = password.value.trim();
    var pwPattern = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
    if (passwordValue.value === "") {
        setError(password, "필수 정보입니다.");
        return false;
    } else if (!pwPattern.test(passwordValue)) {
        setError(password, "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
        return false;
    } else {
        setSuccess(password);
    }
    return true;
}
// 비밀번호 확인 유혀성 검사
function checkPassword2() {
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();
    if (password2Value === "") {
        setError(password2, "필수 정보입니다.");
        return false;
    } else if (password2Value !== passwordValue) {
        setError(password2, "비밀번호가 일치하지 않습니다.");
        return false;
    } else {
        setSuccess(password2);
    }
    return true;
}
// 이름 유효성 검사
function checkName() {
    const nameValue = name.value.trim();
    var namePattern = /^[가-힣]{2,4}$/;
    if (nameValue === "") {
        setError(name, "필수 정보입니다.");
        return false;
    } else if (!namePattern.test(nameValue)) {
        setError(name, "2글자 이상 한글만 입력해 주세요.");
        return false;
    } else {
        setSuccess(name);
    }
    return true;
}

function checkBirthday(){
    const birthdayValue = birthday.value.trim();
    if(birthdayValue === ""){
    setError(birthday,"필수 정보입니다.")
    return false;
    }else{
        setSuccess(birthday)
    }
    return true;
}

// 휴대폰번호 유효성검사
function checkPhone() {
    var phonePattern = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    const phoneValue = document.getElementById("phone").value.trim();
    if (phoneValue === "") {
        setError(phone, "필수 정보입니다.");
        return false;
    } else if (!phonePattern.test(phoneValue)) {
        setError(phone, "형식에 맞지 않은 번호입니다.");
        return false;
    } else {
        setSuccess(phone);
    }
    return true;
}
// 휴대폰 인증번호 유효성 검사
function checkPhone2() {
    const phone2Value = document.getElementById("phone2").value.trim();
    if (phone2Value === "") {
        setError(phone2, "필수 정보입니다.");
        return false;
    } else if (phone2Value !== code2) {
        setError(phone2, "인증번호가 일치하지 않습니다.")
        return false;
    } else {
        setSuccess(phone2);
    }
    return true;
}
// 약관동의 유효성검사
function checkRadio() {
    if (!$("input:checkbox[name='agree']").is(":checked")) {
        setError(agree, "필수 정보입니다.");
        return false
    } else {
        setSuccess(agree);
    }
    return true;
}

// 약관 : 전체동의 / 해제
const agreeChkAll = document.querySelector("input[name=agree_all]");
agreeChkAll.addEventListener("change", (e) => {
    let agreeChk = document.querySelectorAll(
        "input[name=agree],input[name=agree2]"
    );
    for (let i = 0; i < agreeChk.length; i++) {
        agreeChk[i].checked = e.target.checked;
    }
});

// //후대폰 문자보내기
var code2 = "";
$("#phoneChk").click(function () {
    alert("인증번호 발송이 완료되었습니다.\n휴대폰에서 인증번호 확인을 해주십시오.");
    var phone = $("#phone").val();
    $.ajax({
        type: "GET",
        url: "phoneCheck?user_phone_number"
        data : "user_phone_number" : phone
        success: function (data) {
            if (data == "error") {
                alert("휴대폰 번호가 올바르지 않습니다.")
                $(".successPhoneChk").text("유효한 번호를 입력해주세요.");
                $(".successPhoneChk").css("color", "red");
                $("#phone").attr("autofocus", true);
            } else {
                $("#phone2").attr("disabled", false);
                $("#phoneChk2").css("display", "inline-block");
                $(".successPhoneChk").text("인증번호를 입력한 뒤 본인인증을 눌러주십시오.");
                $(".successPhoneChk").css("color", "green");
                code2 = data;
            }
        }
    });
});
// 휴대폰 인증번호 대조
$("#phoneChk2").click(function () {
    if ($("#phone2").val() == code2) {
        $(".successPhoneChk").text("인증번호가 일치합니다.");
        $(".successPhoneChk").css("color", "green");
        $("#phoneDoubleChk").val("true");
        $("#phone2").attr("disabled", true);
    } else {
        $(".successPhoneChk").text("인증번호가 일치하지 않습니다. 확인해주시기 바랍니다.");
        $(".successPhoneChk").css("color", "red");
        $("#phoneDoubleChk").val("false");
        $(this).attr("autofocus", true);
    }
});