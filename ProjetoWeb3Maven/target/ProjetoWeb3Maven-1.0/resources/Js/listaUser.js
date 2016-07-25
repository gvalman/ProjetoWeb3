onload = Carregar;

function Carregar() {
    CloseModalComentarios();
}

function CloseModalComentarios(){
    var modal = document.getElementById('ModalListaComentario');
    
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close-imagem")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    } 
}

function OpenModalListaComentario() {
    // Get the modal
    var modal = document.getElementById('ModalListaComentario');
    modal.style.display = "block";
}

/*Usado para chamar função após a conclusão do AJAX*/
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
            OpenModalListaComentario();
            break;
    }
}