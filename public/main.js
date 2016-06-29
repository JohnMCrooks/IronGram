lastPhotosData = null;

function getPhotos(){
       $.ajax({
            "type":"GET",
            "url": "/photos",
            "success": function(data){
                if (JSON.stringify(lastPhotosData) !== JSON.stringify(data)){

                    $('#photos').empty();
                    for (var i in data){
                        var elem = $("<img>");
                        elem.attr("src", "photos/" + data[i].filename);
                        elem.attr("width", 300);
                        elem.attr("height", 300);
                        $("#photos").append(elem);
                        lastPhotosData = data;
                     }
                 }
            }

       })
}

function login(){
    var data = {
        "name": $("#username").val(),
        "password": $("#password").val()
    };

    $.ajax({
        "type": "POST",
        "data": JSON.stringify(data),
        "contentType": "application/json",
        "url": "/login",
        "success": function(){
            $("#notLoggedIn").hide();
            $("#loggedIn").show();
            getPhotos();
        }
    });
}

function logout(){
    $.ajax({                                // Logout function

            "type": "POST",
            "url": "/logout",
            "success": function(){            //js doesn't care if you leave empty functions
                $("#notLoggedIn").show();
                $("#loggedIn").hide();
            }


        });
}

$.ajax({                            // check to see what should be hidden
    "type": "GET",
    "url": '/user',
    "success": function(data){
        if (data){
            $("#notLoggedIn").hide();
            getPhotos();
        } else {
            $("#loggedIn").hide();
        }

    }
})

setInterval(getPhotos, 1000);