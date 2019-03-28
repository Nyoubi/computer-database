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
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1><c:out value="${numberComputer} Computers found"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

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

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered" style="text-align: center">
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
						<th style="text-align: center">Computer name</th>
						<th style="text-align: center">Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th style="text-align: center">Discontinued date</th>
						<!-- Table header for Company -->
						<th style="text-align: center">Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computerData}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td id="name"><a href="editComputer?id=${computer.getId()}" onclick="">${computer.getName()}</a></td>
							<td>${computer.getIntroduced()}</td>
							<td>${computer.getDiscontinued()}</td>
							<td>${computer.getCompanyName()}</td>
						</tr>
					</c:forEach>
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
					end="${computerPage.getStart()+4}" varStatus="loop">
					<c:choose>
						<c:when test="${loop.index == index}">
							<li class="active"><a
								>${loop.index}</a></li>
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