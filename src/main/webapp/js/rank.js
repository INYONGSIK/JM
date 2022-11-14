let list = null;
     $(document).ready(function() {
        console.log("ajax");
            $.ajax({
                type: 'get',
                url : '/rank2',
                data: {
                    dateValue:"music",
                    genre:"전체"
                },
                success:function(data){
                    list=data;
                    console.log(list)
                    htmlSelectfunc(list)

                },
                error:function(){
                    console.log("error")
                }
	        });
	        $.ajax({
                type: 'get',
                url : '/rank3',
                success:function(data){
                    $("#Title").html(data);
                },
                error:function(){
                    console.log("error")
                }
            });
     });
     $(document).ready(function() {
        console.log("ajax");
        $("#button").click(function(){
            $.ajax({
                type: 'get',
                url : '/rank2',
                data: {
                    dateValue:$("#date").val(),
                    genre:$("#genre").val()
                },
                success:function(data){
                    list=data;
                    console.log(list);
                    htmlSelectfunc(list);

                },
                error:function(){
                    console.log("error")
                }
	        });
	        $.ajax({
                type: 'get',
                url : '/rank3',
                data: {
                    dateValue:$("#date").val()
                },
                success:function(data){
                    $("#Title").html(data);
                },
                error:function(){
                    console.log("error")
                }
            });
        })
     });

     function htmlSelectfunc(list){
            var html = "";
            $.each(list,function(index, item){
                    html +="<tr id='musicRankTable'>"+
                    "<td>"+(index+1)+"</td>"+
                    "<td>"+item.music_title+"</td>"+
                    "<td>"+item.music_singer+"</td>"+
                    "<td>"+item.music_genre+"</td>"+
                    "<td>"+item.music_image+"</td>"+
                    "<td>"+
                    "<audio controls>"+
                        "<source src=" + item.music_file + ">" +
                    "</audio>"
                    + "</td>"+
                    "<td>"+item.music_like+"</td>"+
                    "</tr>"
            });
            $("#musicRankList").html(html);
     }
