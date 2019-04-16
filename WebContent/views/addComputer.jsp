<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
			<strong>Discontinued date must respect the format dd/mm/yyyy
				and be after introduced date</strong>
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
					<form:form modelAttribute="computer" name="addForm" id="addForm"
						action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<form:label path="name" for="name">Computer name</form:label>
								<form:input path="name" type="text"
									class="form-control" id="name" name="name"
									placeholder="Computer name"/>
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced">Introduced date <br />
									<small class="text-muted">(Can be empty)</small>
								</form:label>
								<form:input path="introduced" type="date"
									class="form-control" id="introduced" name="introduced"
									placeholder="Introduced date" />
							</div>
							<div class="form-group" data-toggle="tooltip"
								title="You need to fill introduced first">
								<form:label path="discontinued" for="discontinued">Discontinued date <br />
									<small class="text-muted">(Can be empty)</small>
								</form:label>
								<form:input path="discontinued" type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date" />
							</div>
							<div class="form-group">
								<form:label path="companyId" for="companyId">Company</form:label>
								<form:select path="companyId" class="form-control"
									id="companyId" name="companyId">
									<form:option value="0">--</form:option>
									<c:forEach var="company" items="${listCompanies}">
										<form:option value="${company.id}">${company.name}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"
								id="btnAdd"> or <a href=<c:url value ="/dashboard"/>
								class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/validate.js"/>"></script>

</body>

</html>