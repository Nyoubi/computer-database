<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
						<spring:message code="login.title" />
					</h1>
					<c:if test="${param.error}">
						<div class="alert alert-danger">
							<strong><spring:message code="login.error" /></strong>
						</div>
					</c:if>
					<c:if test="${param.logout}">
						<div class="alert alert-info">
							<strong><spring:message code="login.logout" /></strong>
						</div>
					</c:if>
					<c:if test="${param.created}">
						<div class="alert alert-success">
							<strong><spring:message code="login.created" /></strong>
						</div>
					</c:if>
					<form action="<c:url value="/LoginProcess" />" method="post">
						<fieldset>
							<legend>
								<spring:message code="login.information" />
							</legend>
<%-- 							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
							<div class="form-group">
								<label for="username"><spring:message
										code="login.username" /></label> <input type="text" name="username" />
							</div>
							<div class="form-group">
								<label for="password"><spring:message
										code="login.password" /></label> <input type="password"
									name="password" />
							</div>
							<div class="form-group">
								<input class="btn btn-primary" name="submit" type="submit"
									value="<spring:message code="login.button"/>" /> <a
									class="btn btn-success" id="create"
									href=<c:url value ="/create"/>><spring:message
										code="login.create" /></a>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/i18n.js"/>"></script>

</body>
</html>