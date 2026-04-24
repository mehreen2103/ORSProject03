<%@page import="in.co.rays.project_3.controller.DeviceCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Device View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">

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
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.DEVICE_CTL%>" method="post">

		<div class="row pt-3 pb-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">

				<jsp:useBean id="dto"
					class="in.co.rays.project_3.dto.DeviceDTO"
					scope="request"></jsp:useBean>

				<div class="card">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Device</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Device</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>
						<div class="alert alert-success">
							<%=ServletUtility.getSuccessMessage(request)%>
						</div>
						<%
							}
						%>

						<!-- Error Message -->
						<%
							if (!ServletUtility.getErrorMessage(request).equals("")) {
						%>
						<div class="alert alert-danger">
							<%=ServletUtility.getErrorMessage(request)%>
						</div>
						<%
							}
						%>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Device Session Code -->
						<span><b>Device Session Code</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="deviceSessionCode"
								placeholder="Enter Device Session Code"
								value="<%=DataUtility.getStringData(dto.getDeviceSessionCode())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("deviceSessionCode", request)%>
						</font><br>

						<!-- Device Name -->
						<span><b>Device Name</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="deviceName"
								placeholder="Enter Device Name"
								value="<%=DataUtility.getStringData(dto.getDeviceName())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("deviceName", request)%>
						</font><br>

						<!-- User Name -->
						<span><b>User Name</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="userName"
								placeholder="Enter User Name"
								value="<%=DataUtility.getStringData(dto.getUserName())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("userName", request)%>
						</font><br>

						<!-- Status -->
						<span><b>Status</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="status"
								placeholder="Enter Status"
								value="<%=DataUtility.getStringData(dto.getStatus())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("status", request)%>
						</font><br><br>

						<!-- Buttons -->
						<div class="text-center">
						<%
							if (id > 0) {
						%>
							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=DeviceCtl.OP_UPDATE%>">
							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=DeviceCtl.OP_CANCEL%>">
						<%
							} else {
						%>
							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=DeviceCtl.OP_SAVE%>">
							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=DeviceCtl.OP_RESET%>">
						<%
							}
						%>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4 mb-4"></div>
		</div>
	</form>
	</main>

</body>
<%@include file="FooterView.jsp"%>
</html>