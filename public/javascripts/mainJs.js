verificar();
function verificar() {
	var tipoPeriodo = document.getElementById("tipo-periodo").options[document.getElementById("tipo-periodo").selectedIndex].value;
	if (tipoPeriodo == "dataDef") {
		document.getElementById("container-campo-dataInicial").hidden = false;
		document.getElementById("container-campo-dataFinal").hidden = false;
    } else {
    	document.getElementById("container-campo-dataInicial").hidden = true;
    	document.getElementById("container-campo-dataFinal").hidden = true;
    }
}