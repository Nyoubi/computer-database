$(document).ready(function() {

	var dateFormat = /([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/;

	$("#introducedError").hide();
	$("#discontinuedError").hide();
	$("#discontinued").attr("disabled",true);

	var introduced = true, discontinued = true;
	
	$("#introduced").change(function() {
		if (!($("#introduced").val().match(dateFormat))) {
			$("#introducedError").show();
			document.getElementById("btnAdd").disabled = true;
			introduced = false;
			$("#discontinued").attr("disabled",true);
		} else {
			$("#introducedError").hide();
			introduced = true;
			if ($("#introduced").val() == "") {
				$("#discontinued").val("");
				$("#discontinued").attr("disabled",true);
			} else {
				$("#discontinued").attr("disabled",false);
			}
			if (discontinued) {
				document.getElementById("btnAdd").disabled = false;
			}
		}
	});

	$("#discontinued").change(function() {
		if ($("#discontinued").val() == null || !($("#discontinued").val().match(dateFormat))) {
			$("#discontinuedError").show();
			document.getElementById("btnAdd").disabled = true;
			discontinued = false;

		} else {
			$("#discontinuedError").hide();
			discontinued = true;
			if (introduced) {
				document.getElementById("btnAdd").disabled = false;
			}
		}
	});

});