/**
 * stt.js
 */
 
 $(function(){
	$('#sttForm').on('submit', function(event){
		event.preventDefault(); //submit 후에  reload 안 되게
		formData = new FormData($('#sttForm')[0]);
		
		$.ajax({
			type:"post",
			enctype:"multipart/form-data",
			url:"clovaSTT",
			data:formData,
			processData:false, //필수
			contentType:false, //필수
			success:function(result){
				alert(result);
				data=JSON.parse(result);
				$('#resultDiv').text(data.text);
			},
			error:function(e){
				alert("에러 발생 : " + e);
			}			
		});
	});
	
});