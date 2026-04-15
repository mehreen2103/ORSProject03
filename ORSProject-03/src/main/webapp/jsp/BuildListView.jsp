<%@page import="in.co.rays.project_3.controller.BuildListCtl"%>
<%@page import="in.co.rays.project_3.dto.BuildDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Build List View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.bg-img {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/stars.jpeg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 90px;
}
</style>
</head>

<body class="bg-img">

<%@include file="Header.jsp"%>

<form action="<%=ORSView.BUILD_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BuildDTO" scope="request"></jsp:useBean>

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

	List list = ServletUtility.getList(request);
	Iterator<BuildDTO> it = list.iterator();
%>

<div class="container-fluid">

	<h2 class="text-center text-white mb-4">Build List</h2>

	<!-- Success Message -->
	<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
	<div class="alert alert-success text-center">
		<%=ServletUtility.getSuccessMessage(request)%>
	</div>
	<% } %>

	<!-- Error Message -->
	<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
	<div class="alert alert-danger text-center">
		<%=ServletUtility.getErrorMessage(request)%>
	</div>
	<% } %>

	<!-- Search Panel -->
	
	   <div class="row mb-3">
		<div class="col-md-3">
			<input type="text" name="buildVersion" class="form-control"
				placeholder="Enter Build Version"
				value="<%=ServletUtility.getParameter("buildVersion", request)%>">
		</div>

		<div class="col-md-3">
			<input type="text" name="buildCode" class="form-control"
				placeholder="Enter Build Code"
				value="<%=ServletUtility.getParameter("buildCode", request)%>">
		</div>

		<div class="col-md-3">
			<input type="submit" class="btn btn-primary"
				name="operation" value="<%=BuildListCtl.OP_SEARCH%>">

			<input type="submit" class="btn btn-dark"
				name="operation" value="<%=BuildListCtl.OP_RESET%>">
		</div>
	</div>
	

	<!-- Table -->
	<div class="table-responsive">
		<table class="table table-dark table-bordered table-hover text-center">
			<thead class="thead-light">
				<tr>
					<th><input type="checkbox" id="select_all"> Select All</th>
					<th>S.No</th>
					<th>Build Code</th>
					<th>Build Version</th>
					<th>Triggered By</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>
			</thead>

			<tbody>
			<% while (it.hasNext()) {
				dto = it.next(); %>
				<tr>
					<td>
						<input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>">
					</td>
					<td><%=index++%></td>
					<td><%=dto.getBuildCode()%></td>
					<td><%=dto.getBuildVersion()%></td>
					<td><%=dto.getTriggeredBy()%></td>
					<td><%=dto.getStatus()%></td>
					<td>
						<a class="btn btn-sm btn-info"
							href="BuildCtl?id=<%=dto.getId()%>">Edit</a>
					</td>
				</tr>
			<% } %>
			</tbody>
		</table>
	</div>

	<!-- Pagination -->
	<div class="row mt-3">
		<div class="col-md-2">
			<input type="submit" name="operation"
				class="btn btn-secondary"
				value="<%=BuildListCtl.OP_PREVIOUS%>"
				<%=pageNo > 1 ? "" : "disabled"%>>
		</div>

		<div class="col-md-2">
			<input type="submit" name="operation"
				class="btn btn-primary"
				value="<%=BuildListCtl.OP_NEW%>">
		</div>

		<div class="col-md-2">
			<input type="submit" name="operation"
				class="btn btn-danger"
				value="<%=BuildListCtl.OP_DELETE%>">
		</div>

		<div class="col-md-6 text-right">
			<input type="submit" name="operation"
				class="btn btn-secondary"
				value="<%=BuildListCtl.OP_NEXT%>"
				<%=(nextPageSize != 0) ? "" : "disabled"%>>
		</div>
	</div>

</div>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>