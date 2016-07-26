onload = Carregar;

function Carregar() {
    
    //$("#ResultadoMensagem").hide(6000);
    
    //ModalNovoUser();
    OnLoadModalNewUser();
}


/*Necessário para o funcionamento do modal novo usuairo*/
function OnLoadModalNewUser() {
    /*$("#formNewUser\\:ImagemSaida").attr("src", "");
    $("#formNewUser\\:ImagemSaida").hide();*/
    $("#formNewUser\\:ImagemEntrada").change(function () {
        var oFReader = new FileReader();
        oFReader.readAsDataURL(this.files[0]);
        oFReader.onload = function (oFREvent) {
            $("#formNewUser\\:ImagemSaida").attr("src", oFREvent.target.result);
            $("#formNewUser\\:ImagemSaida").show();
        };
    });
}

function ModalNovoUser() {

    // Get the modal
    var modal = document.getElementById('ModalNovoUser');

// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[1];

    // When the user clicks the button, open the modal 
    $("#NovoUserIcone").click(function () {
        modal.style.display = "block";
        closeNavLeft();
    });

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