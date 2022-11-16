
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
                    var list=data;
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
     /*<![CDATA[*/
            var html = "";
            $.each(list,function(index, item){
                    html +="<tr id='musicRankTable'>"+
                    "<td>"+(index+1)+"</td>"+
                    "<td><a href='/musicDetails/"+
                    item.music_number+
                    "'/>"+
                    item.music_title+
                    "</a></td>"+
                    "<td>"+item.music_singer+"</td>"+
                    "<td>"+item.music_genre+"</td>"+
                    "<td>"+
                    "<img src='/webapp/img/musicimage/" +
                        item.music_image +
                     "' style={width:10px;}/>"+
                     "</td>"+
                    "<td>"+
                    "<audio controls>"+
                    "<source src='/webapp/music/" + item.music_file +  "'/>" +
                    "</audio>" +
                    /*<source th:src="|/webapp/music/${item.music_file}|" type="audio/mp3"></source>+*/
                    "</td>"+
                    "<td>"+item.music_like+"</td>"+
                    "</tr>"
            });
                         /*]]>*/
            $("#musicRankList").html(html);

     }