var clic;
var mistakeMarkup;

function init() {

// Cache la fenêtre de suggestion au démarrage
	$("#suggestions").hide();
	
	// Affiche la liste de suggestions
	showSuggestions();
}
window.onload = init; 



function showSuggestions() {	
	$(".orthoerror, .grammerror").click(function(e) {
		// Récupère l'erreur qui a été clickée
		mistakeMarkup = $(this);
		
		// Récupère les suggestions
		var suggestions = $(this).attr("suggestions");
		suggestionsArray = suggestions.split(",");
		
		// Formate les suggestions
		suggestions = "";
		for (var i=0; i<suggestionsArray.length; i++) {
			suggestions += "<div class=\"suggestion\">" + suggestionsArray[i] + "</div>";
		}
		$("#suggestions").html(suggestions);
		selectCorrection();
		
		// Affiche la popup de suggestion à l'emplacement de la souris
		$("#suggestions").show();
		$("#suggestions").offset({top: e.pageY, left: e.pageX});
		clic = 2;
		
		// Cache les suggestions quand on clic ailleurs
		hideSuggestions();
	});
}


function selectCorrection() {
	$(".suggestion").click(function(e) {
		// Récupère la sélection
		var selection = $(this).text();
		
		// Remplace l'erreur par la correction
		mistakeMarkup.replaceWith(selection);
		
		// Cache la fenêtre de suggestions
		$("#suggestions").hide();
	});
}


function hideSuggestions() {
	$("body").bind("click", function(e) {
		clic--;
		if (clic == 0) {
			$("#suggestions").hide();
			suggestionsAreDisplayed = false;
			$("body").unbind("click");
		}
	});
    
}

function sendCorrectedText() {
	
		if ($('.grammerror, .orthoerror').length == 0) {
        
			var text = $('#correction').text();
			$('#sendCorrectedText input[name="correctedText"]').attr("value", text);
            return true;
		} else {
			alert("Vous devez corriger toutes les erreurs avant de valider !");
			return false;
		}

}




	
