
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<%@include file="/views/imports/header.jsp"%>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error403.denied"/>
				<br/>
				<spring:message code="error403.click"/> 
				<a href="<c:url value="/computer/dashboard" /> ">
				<spring:message code="error403.here"/></a>
				<spring:message code="error403.dashboard"/>
			</div>
		</div>
	</section>

	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/i18n.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</body>
</html>