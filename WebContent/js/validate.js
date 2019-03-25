$(document).ready(function() {

	var dateFormat = /(^$|[0-9]{4}[-][0-9]{2}[-][0-9]{2}$)/;

	$("#introducedError").hide();
	$("#discontinuedError").hide();
	
	var introduced = true, discontinued = true;
	
	$("#introduced").change(function() {
		if ($("#introduced").val() == null || !($("#introduced").val().match(dateFormat))) {
			$("#introducedError").show();
			document.getElementById("btnAdd").disabled = true;
			introduced = false;
			return false;
		} else {
			$("#introducedError").hide();
			introduced = true;
			if (discontinued) {
				document.getElementById("btnAdd").disabled = false;
			}
		}
		return true;
	});

	$("#discontinued").change(function() {
		if ($("#discontinued").val() == null || !($("#discontinued").val().match(dateFormat))) {
			$("#discontinuedError").show();
			document.getElementById("btnAdd").disabled = true;
			discontinued = false;
			return false;
		} else {
			$("#discontinuedError").hide();
			discontinued = true;
			if (introduced) {
				document.getElementById("btnAdd").disabled = false;
			}
		}
		return true;
	});

});