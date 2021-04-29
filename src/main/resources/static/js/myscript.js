
// postavlja tab na zadnji koji je bio prije refresha/reloada
$(document).ready(function () {
        if (sessionStorage.getItem("whichTabIsActive")==='sport') {
        $('#sport-tab').click();
    }
    window.scrollTo(0, 0);
});

// refresh button
$('.btn-warning').on('click', function () {
    location.reload();
});

$('#vijesti-tab').on('click', function () {
    sessionStorage.setItem("whichTabIsActive", "vijesti");
    window.scrollTo(0, 0);
});

$('#sport-tab').on('click', function () {
    sessionStorage.setItem("whichTabIsActive", "sport");
    window.scrollTo(0, 0);
});



// lista portala
$('.link-portal-index').on('click', function () {
    $("#ubacivanje-novog-sadrzaja").load("/ #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if(statusTxt == "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.image').css({'width':'230px', 'height':'123px'});
            $('.logo-portala').attr('src', 'imgs/indexhr-logo.png');
        }
        if(statusTxt == "error") {
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
});

$('.link-portal-net').on('click', function () {
    $("#ubacivanje-novog-sadrzaja").load("/net #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if(statusTxt == "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.image').css({'width':'210px', 'height':'123px'});
            $('.logo-portala').attr('src', 'imgs/nethr-logo.png');
        }
        if(statusTxt == "error") {
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
});

$('.link-portal-narod').on('click', function () {
    $("#ubacivanje-novog-sadrzaja").load("/narod #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if(statusTxt == "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.image').css({'width':'230px', 'height':'155px'});
            $('.logo-portala').attr('src', 'imgs/narodhr-logo.png');
        }
        if(statusTxt == "error") {
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
});



$('.link-portal-dnevno').on('click', function () {
    $("#ubacivanje-novog-sadrzaja").load("/dnevno #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if(statusTxt == "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.image').css({'width':'230px', 'height':'118px'});
            $('.logo-portala').attr('src', 'imgs/dnevnohr-logo.png');
        }
        if(statusTxt == "error") {
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
});


$('.link-portal-rep').on('click', function () {
    $("#ubacivanje-novog-sadrzaja").load("/rep #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if(statusTxt == "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.image').css({'width':'190px', 'height':'110px'});
            $('.logo-portala').attr('src', 'imgs/rephr-logo.png');

            // na ovom portalu nema sporta pa se hidea sport tab
            $('#sport-tab').hide()
        }
        if(statusTxt == "error") {
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
});


$('.link-portal-ict').on('click', function () {
    $("#ubacivanje-novog-sadrzaja").load("/ict #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if(statusTxt == "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.image').css({'width':'190px', 'height':'110px'});
            $('.logo-portala').attr('src', 'imgs/ictinfo-logo.png');

            // na ovom portalu nema sporta pa se hidea sport tab
            $('#sport-tab').hide()
        }
        if(statusTxt == "error") {
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
});