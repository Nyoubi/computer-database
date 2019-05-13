<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container form-horizontal">
		<a class="navbar-brand pull-left" href="/webapp/computer/dashboard">
			<spring:message code="dashboard.title" />
		</a> <span class="pull-right"> <a href="#"
			onClick="changeLanguage('en')"> <img class="lang-flag" alt="<spring:message code='header.flagen'/>"
				src="<c:url value="/assets/en.png" />" />
		</a> <a href="#" onClick="changeLanguage('fr')"><img class="lang-flag"
				alt="<spring:message code='header.flagfr'/>" src="<c:url value="/assets/fr.png" />" /> </a>
		</span>
		<sec:authorize access="hasAnyAuthority('ADMIN','USER')">
			<div class="pull-right logout">
				<a class="btn btn-default" href="<c:url value="/LogoutProcess"/>">
					<strong><spring:message code="login.logoutButton" /></strong>
				</a>
			</div>
		</sec:authorize>
	</div>
</header>