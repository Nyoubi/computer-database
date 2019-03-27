$(function() {

	let dateFormat = /([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/
	$("#introducedError").hide();
	$("#nameError").hide();
	$("#discontinuedError").hide();
	$("#companyIdError").hide();
	
	$("#discontinued").attr('readonly', true)
	validate($("#introduced"))
	validate($("#discontinued"))
	validate($("#companyId"))
	
	$("#name").on("blur" , function(){
		if (!($(this).val() == "" )) {
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
				$("#discontinued").attr('readonly', true)
			} else {
				$("#discontinued").attr('readonly', false)
			}
			
		} else {
			$("#introducedError").show();
			invalidate($(this))
		}
	});
	
	$("#discontinued").on("blur" , function(){
		if (!$(this).attr("readonly") && dateFormat.test($(this).val()) || ($(this).val() == "" )) {
			
			$("#discontinuedError").hide();
			validate($(this));
			
		} else {
			$("#discontinuedError").show();
			invalidate($(this));
		}
	});
	
	$("#companyId").on("blur", function() {
		if (!$("#companyId option").contains( $("#companyId").val()) ){
			invalidate($(this));
			$("#companyIdError").hide();

		} else {
			validate($(this));
			$("#companyIdError").hide();
		}
	})
	
	$('form[action=addComputer"]').first().on("submit", function(){
		ctx = $(this)
		$(this).find("fieldset div").forEach( childInput => {
			if ( $(this).hasClass('has-error') || !$(this).hasClass('has-success') ){
				ctx.preventDefault()
			}
		})
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

});