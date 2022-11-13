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
     });
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