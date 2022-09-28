// window.onload = function(){
//     setTimeout(loadAfterTime, 2000)
//  };

//  function loadAfterTime() { 
//     var allHeaders = document.querySelectorAll('th');
//     for (var i = 0; i < allHeaders.length; i++) {
//       allHeaders[i].setAttribute('role','cell');
//     }
// }

// document.addEventListener("DOMContentLoaded", function(){    
//     var allHeaders = document.querySelectorAll('th');
//     for (var i = 0; i < allHeaders.length; i++) {
//       allHeaders[i].setAttribute('role','cell');
//     }
// });


var ___ENV = 'test';

function openGeomapWindow(geomapGuid, viewOnly) {
    var geoServer = document.URL.substring(0, document.URL.indexOf('/', 10)+1);
    //if(___ENV=='dev') geoServer = 'http://192.168.2.52:8080/'; // for debug purposes only!!!
    window.open(geoServer + 'cartografico-meetpad/cartografia/index.jsp?map=meetPad&geomap_guid='+geomapGuid+'&viewonly='+viewOnly, '_blank', 'location=yes,scrollbars=yes,status=yes,width='+screen.availWidth+',height='+screen.availHeight);
}


function togglePwdView(e) {
    $("#passwordtooglefield").toggleClass("fa-eye fa-eye-slash");
    var input = $("input[id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_PWD.']");
    if (input.attr("type") == "password")
        input.attr("type", "text");
    else
        input.attr("type", "password");
}



  
       

    
