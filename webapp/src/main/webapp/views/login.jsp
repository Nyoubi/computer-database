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
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="login.title" />
					</h1>

					<c:if test="${error}">
						<div>
							<spring:message code="login.error" />
						</div>
					</c:if>
					<c:if test="${logout}">
						<div>
							<spring:message code="login.logout" />
						</div>
					</c:if>

					<form action="<c:url value="/LoginProcess" />" method="post">
						<fieldset>
							<legend>
								<spring:message code="login.information" />
							</legend>
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
								<input name="submit" type="submit"
									value="<spring:message code="login.button"/>" />
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