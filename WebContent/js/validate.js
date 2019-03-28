$(function() {

	let dateFormat = /([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/
	$("#introducedError").hide();
	$("#nameError").hide();
	$("#discontinuedError").hide();
	$("#companyIdError").hide();
	
	validate($("#introduced"))
	validate($("#discontinued"))
	validate($("#companyId"))
	
	$("#name").on("blur" , function(){
		if (!($(this).val() == "")) {
			$("#nameError").hide();
			validate($(this));
		} else {
			$("#nameError").show();
			invalidate($(this));
		}
	});
	
	$("#introduced").on("blur" , function(){
		if (dateFormat.test($(this).val()) || $(this).val() == "") {
			validate($(this));
			$("#introducedError").hide();
			
			if ($(this).val() == "") {
				$("#discontinued").val("");
			}
			
			if (stringToDate($(this).val()) <= stringToDate($("#discontinued").val())) {
				$("#discontinuedError").hide();
				validate($("#discontinued"));
			}
			
		} else {
			$("#introducedError").show();
			invalidate($(this))
		}
	});
	
	$("#discontinued").on("blur" , function(){
		if ((dateFormat.test($(this).val()) && stringToDate($(this).val()).getTime() >= stringToDate($("#introduced").val()).getTime())
				|| $(this).val() == "") {
			$("#discontinuedError").hide();
			validate($(this));
			
		} else {
			console.log(stringToDate($(this).val()).getTime() >= stringToDate($("#introduced").val()).getTime());
			$("#discontinuedError").show();
			invalidate($(this));
		}
	});
	
	$("#companyId").on("blur", function() {
		if($("#companyId option").toArray().map(option => option.value).indexOf( $("#companyId").val()) == -1){
			invalidate($(this));
			$("#companyIdError").hide();
		} else {
			validate($(this));
			$("#companyIdError").hide();
		}
	})
	
	function invalidate(obj){
		obj.parent().addClass('has-error')
		obj.parent().removeClass('has-success')
		$("form input[type='submit']").first().addClass('disabled')
	}
	
	function validate(obj){
		obj.parent().removeClass('has-error')
		obj.parent().addClass('has-success')
		$("form input[type='submit']").first().removeClass('disabled')
	}
	
	function stringToDate(stringDate) {
		  const [year, month, day] = stringDate.split("-")
		  return new Date(year, month - 1, day)
		}

});