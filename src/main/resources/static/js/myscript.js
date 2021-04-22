
$(document).ready(function () {
        if (sessionStorage.getItem("whichTabIsActive")==='sport') {
        $('#sport-tab').click();
    }
});


$('.btn-warning').on('click', function () {
    location.reload();
});

$('#vijesti-tab').on('click', function () {
    sessionStorage.setItem("whichTabIsActive", "vijesti");
});

$('#sport-tab').on('click', function () {
    sessionStorage.setItem("whichTabIsActive", "sport");
});