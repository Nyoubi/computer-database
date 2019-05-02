
<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="/webapp/computer/dashboard"> 
		<spring:message code="dashboard.title" />
		</a>
	</div>
	<div id="lang">
		<a href="#" onClick="changeLanguage('en')"><img class="lang-flag"
			src="<c:url value="/assets/en.png" />" /></a> <a href="#"
			onClick="changeLanguage('fr')"><img class="lang-flag"
			src="<c:url value="/assets/fr.png" />" /></a>
	</div>
</header>