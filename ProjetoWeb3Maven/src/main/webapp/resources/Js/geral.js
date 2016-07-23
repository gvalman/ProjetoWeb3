function ModalImagem() {
    // Get the modal
    var modal = document.getElementById('ModalImagem');

// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close-imagem")[0];

// When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    }
}

function OpenModalImagem(id) {
    // Get the modal
    var modal = document.getElementById('ModalImagem');

// Get the image and insert it inside the modal - use its "alt" text as a caption
    var Img = document.getElementById(id);
    var modalImg = document.getElementById("img01");
    var captionText = document.getElementById("caption");

    modal.style.display = "block";
    modalImg.src = Img.src;
    modalImg.alt = Img.alt;
    captionText.innerHTML = Img.alt;
}

function openNavLeft() {
    document.getElementById("MenuSideLeft").style.width = "250px";
    document.getElementById("map").style.marginLeft = "250px";
}

function closeNavLeft() {
    document.getElementById("MenuSideLeft").style.width = "0";
    document.getElementById("map").style.marginLeft = "0";
}

function openNavRight() {
    document.getElementById("MenuSideRight").style.width = "250px";
    document.getElementById("map").style.marginRight = "250px";
}

function closeNavRight() {
    document.getElementById("MenuSideRight").style.width = "0";
    document.getElementById("map").style.marginRight = "0";
}

//Se for com o google Maps
function initMap() {

    var dados = {
        "type": "FeatureCollection",
        "features":
                [
                    {
                        "type": "Feature",
                        "id": 0,
                        "properties":
                                {
                                    "bairro_codigo": 728,
                                    "bairro_nome_ca": "CIDADE UNIVERSITARIA",
                                    "rpa": 4,
                                    "microrregiao": 3,
                                    "bairro_nome": "Cidade UniversitÃ¡ria"},
                        "geometry": {
                            "type": "Polygon",
                            "coordinates": [[[-34.944159036934906, -8.04984630501871], [-34.944191519904813, -8.050204895163283], [-34.944197438955229, -8.05027023442114], [-34.944311207572177, -8.051585950694623], [-34.944311317831541, -8.051594802624184], [-34.944311753991926, -8.051629758340791], [-34.942945018203595, -8.052009873555523], [-34.943152163579782, -8.053576771888464], [-34.943158125840441, -8.053621878336617], [-34.946019978138828, -8.052832348362527], [-34.946217119625423, -8.052791992945341], [-34.946237917117543, -8.052970794599196], [-34.946288170803314, -8.053987267004896], [-34.946298521226566, -8.054476939951423], [-34.946324220125831, -8.055943328908358], [-34.946325121713478, -8.056691220208132], [-34.946311149176296, -8.057119794530944], [-34.946224083152316, -8.057900039832079], [-34.946125443483467, -8.058494032047562], [-34.94581855443213, -8.059655921762026], [-34.94591073035231, -8.059624980658363], [-34.946159087532585, -8.059578756366514], [-34.953049943082334, -8.057477750094716], [-34.954503194483706, -8.057033580899528], [-34.95460995209136, -8.0569965060948], [-34.954610010640778, -8.05699679872588], [-34.954618026817435, -8.057036583642304], [-34.954695259676626, -8.057419853663125], [-34.955292747991756, -8.059550379835674], [-34.955293593064958, -8.059550125384217], [-34.957792880398472, -8.058794949292334], [-34.958150887035949, -8.058710739977418], [-34.958175606181022, -8.058705039434036], [-34.958163937341894, -8.058694026990974], [-34.958174843786587, -8.058579702179031], [-34.958303425846495, -8.058372450163381], [-34.958449280929827, -8.057984309961201], [-34.958489627502324, -8.057751650992662], [-34.958519595058469, -8.057516458764155], [-34.958577119692187, -8.057339658039608], [-34.958607165053834, -8.057326421266946], [-34.958626118864501, -8.057295079802026], [-34.95876960909505, -8.056801391401054], [-34.958747492710835, -8.056801868620806], [-34.958420578061236, -8.056866025237547], [-34.958386900280153, -8.056868768949288], [-34.957773453615914, -8.057026675879428], [-34.957734606298587, -8.057032027979458], [-34.957682474767324, -8.056964857952645], [-34.957614459280421, -8.05687722029802], [-34.957137434465054, -8.056262573563574], [-34.957104998551024, -8.056247590189427], [-34.956536618993461, -8.056413395646244], [-34.956515136158202, -8.056359957118966], [-34.956507893765384, -8.056333811498368], [-34.955963268563053, -8.054367980493922], [-34.955687526855314, -8.053372679780406], [-34.954187415859799, -8.047957826202806], [-34.953995129784921, -8.047263722143265], [-34.95382696308323, -8.046657133884317], [-34.953753354826972, -8.046384675158871], [-34.95364704739449, -8.046385139494859], [-34.950887449907206, -8.046397187104468], [-34.947888796196537, -8.046411426349375], [-34.945525277293022, -8.046412587500031], [-34.945472306969407, -8.046412624270447], [-34.944906656963504, -8.046389685548919], [-34.944828951720986, -8.046386533310644], [-34.944792057787112, -8.046385036503764], [-34.944769041940596, -8.046382461414124], [-34.944307467215928, -8.04633082787678], [-34.94384353603111, -8.046286816145811], [-34.943936360590151, -8.047385350420747], [-34.944017962093369, -8.048288805767239], [-34.944159036934906, -8.04984630501871]]]
                        }
                    }
                ]
    };
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: {lat: -8.0631, lng: -34.8712}
    });
    var infowindow = new google.maps.InfoWindow();
    google.maps.event.addListener(map, 'click', function () {
        infowindow.close();
    });
    /*
     // When the user clicks, open an infowindow
     map.data.addListener('click', function (event) {
     var myHTML = event.feature.getProperty("Description");
     infowindow.setContent("<div style='width:150px; text-align: center;'>" + myHTML + "</div>");
     infowindow.setPosition(event.feature.getGeometry().get());
     infowindow.setOptions({pixelOffset: new google.maps.Size(0, -30)});
     infowindow.open(map);
     });
     */
    map.data.addGeoJson(dados); /*Para arquivo GEOJSON local*/
    //map.data.loadGeoJson(dados);/*Para arquivo GEOJSON de URL*/

    // Set the stroke width, and fill color for each polygon
    map.data.setStyle({
        fillColor: 'green',
        strokeWeight: 1
    });
}

function loadBairros() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log(xhttp.responseText);
        }
    };
    xhttp.open("POST", "http://dados.recife.pe.gov.br/storage/f/2013-07-15T15%3A17%3A15.285Z/bairros.geojson", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(null);
}

function ChamarBotao(CodBairro, NomeBairro, tipo) {
    //alert(CodBairro + " " + NomeBairro);
    //console.log(CodBairro + " " + NomeBairro);
    var jsfCommandLink = document.getElementById("ChamarLista");
    $('#CodigoBairroLista').val(CodBairro);
    $('#TipoLista').val(tipo);
    //jsfCommandLink.value = CodBairro;
    jsfCommandLink.click();
}

function handleAjax(data) {
    var status = data.status;

    switch (status) {
        case "begin":
            // This is invoked right before ajax request is sent.
            break;

        case "complete":
            // This is invoked right after ajax response is returned.
            break;

        case "success":
            // This is invoked right after successful processing of ajax response and update of HTML DOM.
            $("#ListaComentario img").each(function () {
                 $(this).attr("src",$(this).attr("longdesc"));
                 $(this).attr("longdesc","OK");
                 console.log($(this).attr("src"));
            });

/*
            for (var i = 0; i < $("#ListaComentario img").size(); i++) {
                console.log($("#ListaComentario img")[i].attr("alt"));
            }*/
            break;
    }
}