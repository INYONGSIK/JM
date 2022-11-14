var list = null;
$(document).ready(function() {
        console.log("ajax");
            $.ajax({
                type: 'get',
                url : '/mainRank',
                success:function(data){
                    list=data;
                    console.log(list);
                    rankFunc(list);
                },
                error:function(){
                    console.log("error")
                }
	        });
	        $.ajax({
                type:'get',
                url:'/followee',
                data:$('#followerListBtn').val(),
                success:function(data){
                    followeeListFunc(data);
                },
                error:function(){
                    console.log("error");
                }
            });
     });
 $(document).ready(function() {
         console.log("ajax2");
         $("#unfollow").click(function(){
             $.ajax({
                 type: 'get',
                 url : '/deleteFollowee',
                 success:function(data){
                     followeeFunc();
                 },
                 error:function(){
                     console.log("error")
                 }
            });
         })
      });
function followeeFunc(){
            console.log("followeeFunc")
            $.ajax({
	            type:'get',
	            url:'/followee',
	            success:function(data){
	                followeeListFunc(data);
	            },
	            error:function(){
	                console.log("error");
	            }
	        })
}
function rankFunc(list){
            var html = "";
            $.each(list,function(index, item){
                    if(index == 10){
                       return false;
                    }
                    html +="<tr>"+
                    "<td>"+(index+1)+"</td>"+
                    "<td>"+item.music_title+"</td>"+
                    "<td>"+item.music_singer+"</td>"
                    "</tr>"
            });
            $("#mainRank").html(html);
     }
function followeeListFunc(data){
            var html = "";
            $.each(data,function(index, item){
                    html += "<tr>"+
                    "<td>"+item.user_nickName+"</td>"+
                    "<td>"+
                    "<input type='submit' class='form-control' value='팔로우 취소' id='unfollow'>"
                    "</td>"
                    "</tr>"
            });
            $("#followeeList").html(html);
}
