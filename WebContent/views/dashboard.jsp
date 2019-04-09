<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
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
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1>
				<c:out value="${numberComputer} Computers found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href=<c:url value ="/addComputer"/>>Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="dashboard" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered"
				style="text-align: center">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th style="text-align: center">Computer name <a
							title="Order by name ascendant"
							href="${computerPage.getOrder('nameAsc')}"><i
								class="fa fa-arrow-down"></i></a> <a
							title="Order by name descendant"
							href="${computerPage.getOrder('nameDesc')}"><i
								class="fa fa-arrow-up"></i></a>
						</th>
						<th style="text-align: center">Introduced date <a
							title="Order by introducted date ascendant"
							href="${computerPage.getOrder('introAsc')}"><i
								class="fa fa-arrow-down"></i></a> <a
							title="Order by introducted date descendant"
							href="${computerPage.getOrder('introDesc')}"><i
								class="fa fa-arrow-up"></i></a>
						</th>
						<!-- Table header for Discontinued Date -->
						<th style="text-align: center">Discontinued date <a
							title="Order by discontinued ascendant"
							href="${computerPage.getOrder('disconAsc')}"><i
								class="fa fa-arrow-down"></i></a> <a
							title="Order by discontinued descendant"
							href="${computerPage.getOrder('disconDesc')}"><i
								class="fa fa-arrow-up"></i></a>
						</th>
						<!-- Table header for Company -->
						<th style="text-align: center">Company <a
							title="Order by company name ascendant"
							href="${computerPage.getOrder('companyAsc')}"><i
								class="fa fa-arrow-down"></i></a> <a
							title="Order by company name descendant"
							href="${computerPage.getOrder('companyDesc')}"><i
								class="fa fa-arrow-up"></i></a>
						</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:if test="${computerData != null}">
						<c:forEach items="${computerData}" var="computer">
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${computer.getId()}"></td>
								<td id="name"><a href="editComputer?id=${computer.getId()}"
									onclick="">${computer.getName()}</a></td>
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
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/dashboard.js"/>"></script>

</body>
</html>