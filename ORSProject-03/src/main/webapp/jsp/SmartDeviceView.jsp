<%@page import="in.co.rays.project_3.controller.SmartDeviceCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Smart Device View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/stars.jpeg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}

.card-body {
	background-image: linear-gradient(to bottom right, #472372, #e4d0ff);
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
		<%@include file="calendar.jsp"%>

	</div>

	<div>

		<main>

		<form action="<%=ORSView.SMARTDEVICE_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SmartDeviceDTO"
				scope="request">
			</jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								if (dto.getDeviceName() != null && dto.getId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">UPDATE
								SMART DEVICE</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">ADD SMART
								DEVICE</h3>

							<%
								}
							%>

							<h4 align="center">

								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>

								<div class="alert alert-success alert-dismissible">

									<button type="button" class="close" data-dismiss="alert">
										&times;</button>

									<%=ServletUtility.getSuccessMessage(request)%>

								</div>

								<%
									}
								%>

							</h4>

							<h4 align="center">

								<%
									if (!ServletUtility.getErrorMessage(request).equals("")) {
								%>

								<div class="alert alert-danger alert-dismissible">

									<button type="button" class="close" data-dismiss="alert">
										&times;</button>

									<%=ServletUtility.getErrorMessage(request)%>

								</div>

								<%
									}
								%>

							</h4>

							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Device Name -->

							<span class="pl-sm-5"> <b>Device Name</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-microchip" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="deviceName"
										placeholder="Enter Device Name"
										value="<%=DataUtility.getStringData(dto.getDeviceName())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("deviceName", request)%>
							</font> <br>

							<!-- Room -->

							<span class="pl-sm-5"> <b>Room</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-home" style="font-size: 1rem;"></i>

										</div>

									</div>

									<%
										HashMap roomMap = new HashMap();

										roomMap.put("Living Room", "Living Room");
										roomMap.put("Bedroom", "Bedroom");
										roomMap.put("Kitchen", "Kitchen");
										roomMap.put("Office", "Office");

										String roomList = HTMLUtility.getList("room", dto.getRoom(), roomMap);
									%>

									<%=roomList%>

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("room", request)%>
							</font> <br>

							<!-- Double Usage -->

							<span class="pl-sm-5"> <b>Device Usage</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-bolt" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="doubleUsage"
										placeholder="Enter Device Usage"
										value="<%=DataUtility.getStringData(dto.getDoubleUsage())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("doubleUsage", request)%>
							</font> <br>

							<!-- Status -->

							<span class="pl-sm-5"> <b>Status</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-info-circle" style="font-size: 1rem;"></i>

										</div>

									</div>

									<%
										HashMap statusMap = new HashMap();

										statusMap.put("Active", "Active");
										statusMap.put("Inactive", "Inactive");
										statusMap.put("Maintenance", "Maintenance");
										statusMap.put("Offline", "Offline");

										String statusList = HTMLUtility.getList("status", dto.getStatus(), statusMap);
									%>

									<%=statusList%>

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
							</font> <br>

							<%
								if (dto.getDeviceName() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=SmartDeviceCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=SmartDeviceCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=SmartDeviceCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=SmartDeviceCtl.OP_RESET%>">

							</div>

							<%
								}
							%>

						</div>

					</div>

				</div>

				<div class="col-md-4 mb-4"></div>

			</div>

		</form>

		</main>

	</div>

</body>

<%@include file="FooterView.jsp"%>

</html>