var list = null;
var follow_list = null;
var user_number = null;
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

     });
$(document).on("click","[id^=unfollow]",(function(e){
                     alert('팔로우 취소 완료.');
                       var followee = $(this).attr("value");
                       var user_number = $("input[name='user_number']").val();
                       console.log(followee);
                       console.log(user_number);
                       $.ajax({
                           type: 'get',
                           url : '/deleteFollowee',
                           data : {follower:user_number,followee:followee},
                           success:function(data){
                                followeeFunc(user_number);
                           },
                           error:function(){
                               console.log("error")
                           }
                      });
                     })
                    );
  $("#followerListBtn").click(function(){
     user_number = $('#followerListBtn').val()
     console.log(user_number);
     $.ajax({
          type:'get',
          url:'/followee',
          data:{follower : user_number},
          success:function(data){
              follow_list=data;
              console.log(data);
              followeeListFunc(follow_list,user_number);
          },
          error:function(){
              console.log("error");
          }
      });
  })

function followeeFunc(user_number){
            console.log("followeeFunc")
            $.ajax({
	            type:'get',
	            url:'/followee',
	            data:{follower : user_number},
	            success:function(data){
	                followeeListFunc(data,user_number);
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
function followeeListFunc(data,user_number){
            var html = "";
            $.each(data,function(index, item){
                    html += "<tr>"+
                    "<td>"+item.user_nickname+"</td>"+
                    "<td>"+
                    "<input type='hidden' value='"+
                     user_number+
                     "' name='user_number'/>"+
                    "</td>"+
                    "<td>"+
                    "<button type='button' class='form-control' value='"+
                    item.user_number
                    +"' name='unfollow' id='unfollow"+
                    index+
                    "'>팔로우 취소"+
                    "</button>"+
                    "</td>"+
                    "</tr>"
            });
            $("#followeeList").html(html);
    }
