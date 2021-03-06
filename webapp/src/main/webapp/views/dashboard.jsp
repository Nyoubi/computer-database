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
			<c:choose>
				<c:when test="${numberComputer>0}">
					<h1>
						${numberComputer}
						<spring:message code="dashboard.number_computer" />
					</h1>
				</c:when>
				<c:otherwise>
					<h1>
						<spring:message code="dashboard.no_number_computer" />
					</h1>
				</c:otherwise>
			</c:choose>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message code='dashboard.filter_placeholder'/>" />
						<input type="submit" id="searchsubmit"
							value="<spring:message code="dashboard.filter"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<sec:authorize access="hasAuthority('ADMIN')">
					<div class="pull-right">
						<a class="btn btn-success" id="addComputer"
							href=<c:url value ="/computer/addComputer"/>><spring:message
								code="dashboard.add_computer" /></a> <a class="btn btn-default"
							id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
							<spring:message code="dashboard.edit" />
						</a> <a class="btn btn-default" id="viewComputer" href="#"
							onclick="$.fn.toggleEditMode();" style="display: none"> <spring:message
								code="dashboard.view" />
						</a>
					</div>
				</sec:authorize>

			</div>
		</div>
		<form id="deleteForm" action="<c:url value="deleteComputer"/>"
			method="POST">
			<input type="hidden" name="selection" value="">
		</form>
		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered text-center">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<sec:authorize access="hasAuthority('ADMIN')">

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <em
										class="fa fa-trash-o fa-lg"></em>
								</a>
							</span></th>
						</sec:authorize>

						<th><spring:message code="dashboard.computer_name" /><a
							title="Order by name ascendant"
							href="${computerPage.getOrder('nameAsc')}"><em
								class="fa fa-arrow-down"></em></a> <a
							title="Order by name descendant"
							href="${computerPage.getOrder('nameDesc')}"><em
								class="fa fa-arrow-up"></em></a></th>
						<th><spring:message code="dashboard.introduced_date" /> <a
							title="Order by introducted date ascendant"
							href="${computerPage.getOrder('introAsc')}"><em
								class="fa fa-arrow-down"></em></a> <a
							title="Order by introducted date descendant"
							href="${computerPage.getOrder('introDesc')}"><em
								class="fa fa-arrow-up"></em></a></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="dashboard.discontinued_date" /> <a
							title="Order by discontinued ascendant"
							href="${computerPage.getOrder('disconAsc')}"><em
								class="fa fa-arrow-down"></em></a> <a
							title="Order by discontinued descendant"
							href="${computerPage.getOrder('disconDesc')}"><em
								class="fa fa-arrow-up"></em></a></th>
						<!-- Table header for Company -->
						<th><spring:message code="dashboard.company" /> <a
							title="Order by company name ascendant"
							href="${computerPage.getOrder('companyAsc')}"><em
								class="fa fa-arrow-down"></em></a> <a
							title="Order by company name descendant"
							href="${computerPage.getOrder('companyDesc')}"><em
								class="fa fa-arrow-up"></em></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:if test="${computerData != null}">
						<c:forEach items="${computerData}" var="computer">
							<tr>
									<td class="editMode"><input type="checkbox" name="cb"
										class="cb" value="${computer.getId()}"></td>
								<td id="name"><sec:authorize access="hasAuthority('ADMIN')">
										<a href="editComputer?id=${computer.getId()}" onclick="">${computer.getName()}</a>
									</sec:authorize> <sec:authorize access="hasAuthority('USER')">
										${computer.getName()}
								</sec:authorize></td>
								<td>${computer.getIntroduced()}</td>
								<td>${computer.getDiscontinued()}</td>
								<td>${computer.getCompanyName()}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${index != computerPage.previousIndex()}">
					<li><a id="previous" href="${computerPage.previousPage()}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:forEach begin="${computerPage.getStart()}"
					end="${computerPage.getStart()+computerPage.getEnd()}"
					varStatus="loop">
					<c:choose>
						<c:when test="${loop.index == index}">
							<li class="active"><a>${loop.index}</a></li>
						</c:when>
						<c:when test="${loop.index != index}">
							<li><a href="${computerPage.indexAt(loop.index)}">${loop.index}</a></li>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:if test="${index != computerPage.nextIndex()}">
					<li><a id="next" href="${computerPage.nextPage()}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>

			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:forEach items="${computerPage.getSizeList()}" var="item">
					<c:choose>
						<c:when test="${item == size}">
							<a id="currentSize" class="active btn btn-default"
								href="${computerPage.setSize(item)}">${item}</a>
						</c:when>
						<c:when test="${item != size}">
							<a class="btn btn-default" href="${computerPage.setSize(item)}">${item}</a>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</footer>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/dashboard.js"/>"></script>
	<script src="<c:url value="/js/i18n.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</body>
</html>