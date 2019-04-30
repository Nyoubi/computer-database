<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="dashboard.title"/></title>
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
	<%@include file="/views/imports/header.jsp"%>
	<%@include file="/views/imports/error.jsp"%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="addComputer.heading"/></h1>
					<form:form modelAttribute="computer" name="addForm" id="addForm"
						action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<form:errors id = "name.errors" path="name" cssClass="alert alert-danger" element="p"/>
								<form:label path="name" for="name"><spring:message code="addComputer.computer_name"/></form:label>
								<spring:message code='addComputer.computer_name' var='computer_name' />
								<form:input path="name" type="text"
									class="form-control" id="name" name="name"
									placeholder="${computer_name}"
								/>
							</div>
							<div class="form-group">
								<form:errors id = "introduced.errors" path="introduced" cssClass="alert alert-danger" element="p"/>
								<form:label path="introduced" for="introduced"><spring:message code="addComputer.introduced_date"/><br />
									<small class="text-muted"><spring:message code="addComputer.empty"/></small>
								</form:label>
								<form:input path="introduced" type="date"
									class="form-control" id="introduced" name="introduced"
									/>
							</div>
							<div class="form-group" data-toggle="tooltip"
								title="You need to fill introduced first">
								<form:errors id="discontinued.errors" path="discontinued" cssClass="alert alert-danger" element="p"/>
								<form:label path="discontinued" for="discontinued"><spring:message code="addComputer.discontinued_date"/><br />
									<small class="text-muted"><spring:message code="addComputer.empty"/></small>
								</form:label>
								<form:input path="discontinued" type="date"
									class="form-control" id="discontinued" name="discontinued"
									 />
							</div>
							<div class="form-group">
								<form:errors id="companyId.errors" path="companyId" cssClass="alert alert-danger" element="p"/>
								<form:label path="companyId" for="companyId"><spring:message code="addComputer.company"/></form:label>
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
							<input type="submit" value="
								<spring:message code="addComputer.add"/>" 
								class="btn btn-primary" id="btnAdd"> 
								<spring:message code="addComputer.or"/> <a href=<c:url value ="/computer/dashboard"/>
								class="btn btn-default"><spring:message code="addComputer.cancel"/></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/validate.js"/>"></script>
	<script src="<c:url value="/js/i18n.js"/>"></script>

</body>

</html>