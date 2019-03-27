<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href=<c:url value ="/css/bootstrap.min.css"/> rel="stylesheet"
	media="screen">
<link href=<c:url value ="/css/font-awesome.css"/> rel="stylesheet"
	media="screen">
<link href=<c:url value = "/css/main.css"/> rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href=<c:url value ="/dashboard"/>>
				Application - Computer Database </a>
		</div>
	</header>

	<div class="container">
		<div id="nameError" class="alert alert-danger" role="alert"
			style="display: none">
			<strong>Name must be filled</strong>
		</div>
		<div id="introducedError" class="alert alert-danger" role="alert"
			style="display: none">
			<strong>Introduced date must respect the format dd/mm/yyyy</strong>
		</div>
		<div id="discontinuedError" class="alert alert-danger" role="alert"
			style="display: none">
			<strong>Discontinued date must respect the format dd/mm/yyyy and be after introduced date</strong>
		</div>
		<div id="companyIdError" class="alert alert-danger" role="alert"
			style="display: none">
			<strong>This company is not an option</strong>
		</div>
	</div>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form name="addForm" id="addForm" action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="name">Computer name</label> <input type="text"
									class="form-control" id="name" name="name"
									placeholder="Computer name" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date <br /> 
									<small class="text-muted">(Can be empty)</small>
								</label> 
								<input type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group" data-toggle="tooltip"
								title="You need to fill introduced first">
								<label for="discontinued">Discontinued date <br /> 
									<small class="text-muted">(Can be empty)</small>
								</label> <input type="date" class="form-control" id="discontinued"
									name=discontinued placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach var="company" items="${listCompanies}">
										<option value="${company.id}"
											<c:if test="${company.name == computer.companyName}">selected</c:if>>${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"
								id="btnAdd"> or <a href=<c:url value ="/dashboard"/>
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/validate.js"/>"></script>

</body>

</html>