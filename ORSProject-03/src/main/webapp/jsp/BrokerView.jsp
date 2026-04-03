<%@page import="in.co.rays.project_3.controller.BrokerCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Broker View</title>
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
	<form action="<%=ORSView.BROKER_CTL%>" method="post">

		<div class="row pt-3 pb-3">
			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">

				<jsp:useBean id="dto"
					class="in.co.rays.project_3.dto.BrokerDTO"
					scope="request"></jsp:useBean>

				<div class="card">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Broker</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Broker</h3>
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

						<!-- Broker Name -->
						<span><b>Broker Name</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="brokerName"
								placeholder="Enter Broker Name"
								value="<%=DataUtility.getStringData(dto.getBrokerName())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("brokerName", request)%>
						</font><br>
						
						<span><b>Contact Number</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="contactNumber"
								placeholder="Enter Contact Number"
								value="<%=DataUtility.getStringData(dto.getBrokerName())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("contactNumber", request)%>
						</font><br>

						<!-- Company -->
						<span><b>Company</b><span style="color:red;">*</span></span>
						<div class="col-sm-12">
							<input type="text" class="form-control" name="company"
								placeholder="Enter Company"
								value="<%=DataUtility.getStringData(dto.getCompany())%>">
						</div>
						<font color="red">
							<%=ServletUtility.getErrorMessage("company", request)%>
						</font><br><br>

						<!-- Buttons -->
						<div class="text-center">
						<%
							if (id > 0) {
						%>
							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=BrokerCtl.OP_UPDATE%>">
							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=BrokerCtl.OP_CANCEL%>">
						<%
							} else {
						%>
							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=BrokerCtl.OP_SAVE%>">
							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=BrokerCtl.OP_RESET%>">
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