<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="dashboard.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href=<c:url value ="/css/bootstrap.min.css"/> rel="stylesheet"
	media="screen">
<link href=<c:url value ="/css/font-awesome.css"/> rel="stylesheet"
	media="screen">
<link href=<c:url value = "/css/main.css"/> rel="stylesheet"
	media="screen">
</head>
<body>
	<%@include file="/views/imports/header.jsp"%>


	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				Error 500: An error has occured!
				<br/>
				<spring:message code="${stackTrace}"/>
			</div>
		</div>
	</section>

	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/i18n.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>

</body>
</html>