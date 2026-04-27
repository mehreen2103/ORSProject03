<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.GeoFenceCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>GeoFence View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/stars.jpeg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}

.card-body {
	background-image: linear-gradient(to bottom right, #472372,#e4d0ff);
	box-shadow: 5px 8px 7px #001a33;
	color: white;
	border-radius: 10px;
}

h3.text-center {
	color: black !important;
}
</style>

</head>

<body class="hm">

<%@include file="Header.jsp"%>

<main>
<form action="<%=ORSView.GEOFENCE_CTL%>" method="post">

<jsp:useBean id="dto"
class="in.co.rays.project_3.dto.GeoFenceDTO"
scope="request"></jsp:useBean>

<div class="row pt-3 pb-3">
	<div class="col-md-4"></div>

```
<div class="col-md-4">

	<div class="card">
		<div class="card-body">

			<%
				long id = DataUtility.getLong(request.getParameter("id"));
				if (dto.getId() != null && dto.getId() > 0) {
			%>
			<h3 class="text-center">Update GeoFence</h3>
			<%
				} else {
			%>
			<h3 class="text-center">Add GeoFence</h3>
			<%
				}
			%>

			<!-- Success Message -->
			<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
				<div class="alert alert-success">
					<%=ServletUtility.getSuccessMessage(request)%>
				</div>
			<% } %>

			<!-- Error Message -->
			<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
				<div class="alert alert-danger">
					<%=ServletUtility.getErrorMessage(request)%>
				</div>
			<% } %>

			<input type="hidden" name="id" value="<%=dto.getId()%>">

			<!-- GeoFence Code -->
			<span><b>GeoFence Code</b><span style="color:red;">*</span></span>
			<input type="text" class="form-control" name="geoFenceCode"
				placeholder="Enter GeoFence Code"
				value="<%=DataUtility.getStringData(dto.getGeoFenceCode())%>">
			<font color="red"><%=ServletUtility.getErrorMessage("geoFenceCode", request)%></font><br>

			<!-- Location Name -->
			<span><b>Location Name</b><span style="color:red;">*</span></span>
			<input type="text" class="form-control" name="locationName"
				placeholder="Enter Location Name"
				value="<%=DataUtility.getStringData(dto.getLocationName())%>">
			<font color="red"><%=ServletUtility.getErrorMessage("locationName", request)%></font><br>

			<!-- Radius -->
			<span><b>Radius</b><span style="color:red;">*</span></span>
			<input type="text" class="form-control" name="radius"
				placeholder="Enter Radius"
				value="<%=DataUtility.getStringData(dto.getRadius())%>">
			<font color="red"><%=ServletUtility.getErrorMessage("radius", request)%></font><br>

			<!-- Status (HashMap + HTMLUtility) -->
			<%
				HashMap<String,String> map = new HashMap<>();
				map.put("Active", "Active");
				map.put("Inactive", "Inactive");

				String htmlList = HTMLUtility.getList("status", dto.getStatus(), map);
			%>

			<span><b>Status</b><span style="color:red;">*</span></span>
			<div>
				<%=htmlList%>
			</div>
			<font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font><br><br>

			<!-- Buttons -->
			<div class="text-center">
			<%
				if (id > 0) {
			%>
				<input type="submit" name="operation" class="btn btn-success"
					value="<%=GeoFenceCtl.OP_UPDATE%>">
				<input type="submit" name="operation" class="btn btn-warning"
					value="<%=GeoFenceCtl.OP_CANCEL%>">
			<%
				} else {
			%>
				<input type="submit" name="operation" class="btn btn-success"
					value="<%=GeoFenceCtl.OP_SAVE%>">
				<input type="submit" name="operation" class="btn btn-warning"
					value="<%=GeoFenceCtl.OP_RESET%>">
			<%
				}
			%>
			</div>

		</div>
	</div>

</div>

<div class="col-md-4"></div>
```

</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>
