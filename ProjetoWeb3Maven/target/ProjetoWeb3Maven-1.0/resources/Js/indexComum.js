onload = Carregar;
var map;

function Carregar() {
    //initMap();
    IniciarMapa();
    //loadBairros();

    /*Ao clicar no icone o menu left será aberto*/
    $("#menuIcone").click(function () {
        openNavLeft();
    });

    $("#ResultadoMensagem").hide(3000);

    ModalImagem();
    ModalComentario();
    OnLoadModalNewComentario();
}

function ModalComentario() {

    // Get the modal
    var modal = document.getElementById('ModalComentario');

// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    }

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}

/*Necessário para exibir a imagem do comentario*/
function OnLoadModalNewComentario() {
    $("#formNewComentario\\:ImagemSaidaComentario").attr("src", "");
    $("#formNewComentario\\:ImagemSaidaComentario").hide();
    $("#formNewComentario\\:ImagemEntradaComentario").change(function () {
        var oFReader = new FileReader();
        oFReader.readAsDataURL(this.files[0]);
        oFReader.onload = function (oFREvent) {
            $("#formNewComentario\\:ImagemSaidaComentario").attr("src", oFREvent.target.result);
            $("#formNewComentario\\:ImagemSaidaComentario").show();
        };
    });
}

function AbrirModalComentario(CodBairro, NomeBairro) {
    //alert(CodBairro + " " + NomeBairro);
    // Get the modal
    var modal = document.getElementById('ModalComentario');
    modal.style.display = "block";

    $('#formNewComentario\\:CodigoBairro').val(CodBairro);
    $('#formNewComentario\\:NomeBairro').val(NomeBairro);
    $("#TituloModalComentario").text("Novo Comentário para " + NomeBairro);
}

//Gerando mapa a partir do LEAFLET.js
function IniciarMapa() {
    // Instanciando Leaflet e definindo o elemento HTML que o mapa irá ser renderizado...
    var map = L.map('map').setView([-8.043326721315049, -34.93619875000002], 13);

    // Carregando a camada da fonte de dados (MAPBOX) no mapa (ruas, terrenos, etc.)...
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; Contribuidores do <a href="http://osm.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    var myStyle = {
        "color": "#ff7800",
        "weight": 3,
        "opacity": 0.65
    };

    var myStyle1 = {
        "color": "#00FF7F",
        "weight": 2,
        "opacity": 0.65
    };

    L.geoJson(dados, {
        style: myStyle,
        onEachFeature: function (feature, layer) {

            layer.bindPopup("<h3>" + feature.properties.bairro_nome + "</h3><button type='button' class='btn btn-primary' title='Novo Comentário' onclick='AbrirModalComentario(" + feature.properties.bairro_codigo + ",&#96;" + feature.properties.bairro_nome + "&#96;)'>+</button><br/><i class='fa fa-thumbs-o-up' onclick='openNavRight();ChamarBotao(" + feature.properties.bairro_codigo + ",&#96;" + feature.properties.bairro_nome + "&#96;,'curtiu')' style='font-size:72px;color:green;' title='O que gostou?'></i><i class='fa fa-thumbs-o-down' style='font-size:72px;color:red;' title='O que não gostou?'></i>");

            layer.on('mouseover',
                    function () {
                        $('#BairroRodape').empty();
                        $('#BairroRodape').text(feature.properties.bairro_nome);
                        layer.setStyle(myStyle1);
                    }
            )

            layer.on('click',
                    function () {
                        closeNavRight();
                        closeNavLeft();
                    }
            )

            layer.on('mouseout',
                    function () {
                        $('#BairroRodape').empty();
                        layer.setStyle(myStyle);
                    }
            )
        }
    }).addTo(map);
}
