<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><spring:message code="dashboard.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	type="text/css" media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet"
	type="text/css" media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" media="screen">
</head>
<body>
	<%@include file="/views/imports/header.jsp"%>

	<section id="main">
		<div class="container text-center">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="create.title" />
					</h1>
					<c:if test="${not empty errorMessage}">
						<div class="alert alert-danger">
							<strong><spring:message code="${errorMessage}" /></strong>
						</div>
					</c:if>
					<form:form modelAttribute="user" name="createForm" id="createForm"
						action="createAccount" method="POST">
						<fieldset>
							<legend>
								<spring:message code="create.information" />
							</legend>
							<div class="form-group">
								<form:label path="username" for="username">
									<spring:message code="create.username" />
								</form:label>
								<form:input path="username" type="text" name="username" />
							</div>
							<div class="form-group">
								<form:label for="password" path="password">
									<spring:message code="create.password" />
								</form:label>
								<form:input path="password" type="password" name="password" />
							</div>
							<div class="form-group">
							<input type="submit" value="
								<spring:message code="create.button"/>" 
								class="btn btn-primary" name="submit"> 
								<spring:message code="create.or"/> <a href=<c:url value ="/login"/>
								class="btn btn-default"><spring:message code="create.cancel"/></a>
						</div>
						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/i18n.js"/>"></script>

</body>
</html>