// postavlja tab na zadnji koji je bio prije refresha/reloada
$(document).ready(function () {
    $('.logo-portala').attr('src', 'imgs/index-logo.png');
    window.scrollTo(0, 0);

    $(document).on('click', '#vijesti-tab', function (){
        odabraniTab = "vijesti";
        window.scrollTo(0, 0);
    });

    $(document).on('click', '#sport-tab', function (){
        odabraniTab = "sport";
        window.scrollTo(0, 0);
    });

});


// refresh button
$(document).on('click', '.btn-warning', function () {
    $(".link-portal-" + trenutnoAktivanPortalEndpoint).click();
    window.scrollTo(0, 0);
});





var odabraniTab = "vijesti";
var trenutnoAktivanPortalEndpoint = "index";


// lista portala
$('.link-portal-index').on('click', function () {
    clickOnPortalLink("index");
});

$('.link-portal-net').on('click', function () {
    clickOnPortalLink("net");
});

$('.link-portal-narod').on('click', function () {
    clickOnPortalLink("narod");
});

$('.link-portal-rep').on('click', function () {
    clickOnPortalLink("rep");
});

$('.link-portal-ict').on('click', function () {
    clickOnPortalLink("ict");
});

$('.link-portal-science').on('click', function () {
    clickOnPortalLink("science");
});


function clickOnPortalLink(imePortala) {
    $(document.body).css({'cursor' : 'wait'});
    $("#ubacivanje-novog-sadrzaja").load("/" + imePortala + " #ubacivanje-novog-sadrzaja", function (responseTxt, statusTxt, xhr) {
        if (statusTxt === "success") {
            $('.portal-link-container').css('background-color', '#e0a800');
            $('#vijesti-tab').click();
            window.scrollTo(0, 0);
            $('.logo-portala').attr('src', 'imgs/' + imePortala + '-logo.png');
            trenutnoAktivanPortalEndpoint = imePortala;

            //ako se radi o indexu
            if (imePortala==='index') {
                $('.navigacija-stranice').hide();
            } else {
                $('.navigacija-stranice').removeAttr("hidden");
                $('.link-page1').css('background-color', '#e0a800');
            }

            if (imePortala==='rep' || imePortala==='ict' || imePortala==='science') {
                $('#sport-tab').hide();
            }

            $(document.body).css({'cursor' : 'auto'});

        }
        if (statusTxt === "error") {
            $(document.body).css({'cursor' : 'auto'})
            alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
        }
    });
}



// navigacija po stranicama pojedinih portala
$(document).on('click', '.page-link', function () {
    var odabraniPage;
    var odabraniPageBroj;
    $(document.body).css({'cursor' : 'wait'});

    if ($(this).hasClass('page-right') || $(this).hasClass('page-left')) {
        // trazimo koja je prethodna vrijednost bila odabrana
        var oznaceniElement = $('.page-link').filter(function (index) {
            return $('.page-link')[index].style.backgroundColor === 'rgb(224, 168, 0)';
        });

        var trenutnoOznacenaStranica=$(oznaceniElement).attr('value');

        if ($(this).hasClass('page-right')) {
            if (parseInt(trenutnoOznacenaStranica) < 8) {
                odabraniPageBroj = (parseInt(trenutnoOznacenaStranica) + 1).toString();
            } else {
                odabraniPageBroj = "8";
            }
        } else {
            if (parseInt(trenutnoOznacenaStranica) > 1) {
                odabraniPageBroj = (parseInt(trenutnoOznacenaStranica) - 1).toString();
            } else {
                odabraniPageBroj = "1";
            }
        }

        odabraniPage = $('.page-link').filter(function (index) {
            return $($('.page-link')[index]).hasClass("link-page"+odabraniPageBroj)
        });
    } else {
        odabraniPageBroj = parseInt($(this).attr('value'));
        odabraniPage=this;
    }


    $("#ubacivanje-novih-clanaka").load("/" + trenutnoAktivanPortalEndpoint + "/" + odabraniPageBroj+ " #ubacivanje-novih-clanaka",
        function (responseTxt, statusTxt, xhr) {
            if (statusTxt === "success") {
                $('.page-link').css('background-color', 'white');
                $(odabraniPage).css('background-color', '#e0a800');
                $('.logo-portala').attr("src", "imgs/" + trenutnoAktivanPortalEndpoint + "-logo.png")

                if (odabraniTab==='sport') {
                    $('#vijesti-tab').tab().click();
                    $('#sport-tab').tab().click();
                }

                $(document.body).css({'cursor' : 'auto'});
            }
            if (statusTxt === "error") {
                $(document.body).css({'cursor' : 'auto'})
                alert("Doslo je do pogreske: " + xhr.status + ": " + xhr.statusText);
            }

        });
});