$(document).ready(function() {
    $("#sprawdz_slowo").click(function() {
        if (($("#slowo_polskie").css("display")) != "none") {
            zamien_slowo("polskie");
        }
        else {
            zamien_slowo("obce");
        }
    });
    
    function zamien_slowo($obecne_slowo) {
        if ($obecne_slowo == "polskie") {
            $("#slowo_polskie").css("display", "none");
            $("#slowo_obce").css("display", "block");
        }
        else {
            $("#slowo_polskie").css("display", "block");
            $("#slowo_obce").css("display", "none");
        }
    }
    
    $("#sprawdz_slowo").click(function() {
        $("#pokaz_odpowiedz").css("display", "none");
        $("#wiem_nie_wiem").css("display", "block");
    });
    
    $("#dodaj_slowo").click(function() {
        $("#dodaj_slowo").before("<br><input type='text' class='form-control' id='' placeholder='Słowo w języku polskim'> <input type='text' class='form-control' id='' placeholder='Słowo w języku obcym'> ");
    });
});
