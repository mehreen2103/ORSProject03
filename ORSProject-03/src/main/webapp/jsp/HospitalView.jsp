<%@page import="in.co.rays.project_3.controller.HospitalCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Hospital View</title>

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

		<form action="<%=ORSView.HOSPITAL_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HospitalDTO"
				scope="request">
			</jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								if (dto.getPatientName() != null && dto.getId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">UPDATE
								HOSPITAL</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">ADD
								HOSPITAL</h3>

							<%
								}
							%>

							<h4 align="center">

								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>

								<div class="alert alert-success alert-dismissible">

									<button type="button" class="close" data-dismiss="alert">&times;</button>

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

									<button type="button" class="close" data-dismiss="alert">&times;</button>

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

							<!-- Patient Name -->

							<span class="pl-sm-5"> <b>Patient Name</b> <span
								style="color: red;">*</span>
							</span><br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-user"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="patientName"
										placeholder="Enter Patient Name"
										value="<%=DataUtility.getStringData(dto.getPatientName())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("patientName", request)%>
							</font><br>

							<!-- Doctor Name -->

							<span class="pl-sm-5"> <b>Doctor Name</b> <span
								style="color: red;">*</span>
							</span><br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-user-md"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="doctorName"
										placeholder="Enter Doctor Name"
										value="<%=DataUtility.getStringData(dto.getDoctorName())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("doctorName", request)%>
							</font><br>

							<!-- Disease -->

							<span class="pl-sm-5"> <b>Disease</b> <span
								style="color: red;">*</span>
							</span><br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-heartbeat"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="disease"
										placeholder="Enter Disease"
										value="<%=DataUtility.getStringData(dto.getDisease())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("disease", request)%>
							</font><br>

							<!-- Room Number -->

							<span class="pl-sm-5"> <b>Room Number</b> <span
								style="color: red;">*</span>
							</span><br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-bed"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="roomNumber"
										placeholder="Enter Room Number"
										value="<%=DataUtility.getStringData(dto.getRoomNumber())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("roomNumber", request)%>
							</font><br>

							<%
								if (dto.getPatientName() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=HospitalCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=HospitalCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=HospitalCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md"
									value="<%=HospitalCtl.OP_RESET%>">

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