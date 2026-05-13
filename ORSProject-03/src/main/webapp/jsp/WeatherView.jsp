<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.WeatherCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
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

<title>Weather View</title>

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

	</div>

	<div>

		<main>

		<form action="<%=ORSView.WEATHER_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.WeatherDTO"
				scope="request">
			</jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getCityName() != null && dto.getId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">UPDATE
								WEATHER</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">ADD
								WEATHER</h3>

							<%
								}
							%>

							<H4 align="center">

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

							</H4>

							<H4 align="center">

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

							</H4>

							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Alert Code -->

							<span class="pl-sm-5"> <b>Alert Code</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-bell grey-text" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="alertCode"
										placeholder="Enter Alert Code"
										value="<%=DataUtility.getStringData(dto.getAlertCode())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("alertCode", request)%>

							</font> <br>

							<!-- City Name -->

							<span class="pl-sm-5"> <b>City Name</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-building grey-text" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="cityName"
										placeholder="Enter City Name"
										value="<%=DataUtility.getStringData(dto.getCityName())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("cityName", request)%>

							</font> <br>

							<!-- Temperature -->

							<span class="pl-sm-5"> <b>Temperature</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-thermometer-half grey-text"
												style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="temperature"
										placeholder="Enter Temperature"
										value="<%=DataUtility.getStringData(dto.getTemperature())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("temperature", request)%>

							</font> <br>

							<!-- Status -->

							<span class="pl-sm-5"> <b>Status</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-check-circle grey-text"
												style="font-size: 1rem;"></i>

										</div>

									</div>

									<%
										HashMap map = new HashMap();

										map.put("Active", "Active");

										map.put("Inactive", "Inactive");

										String statusList = HTMLUtility.getList("status", dto.getStatus(), map);
									%>

									<%=statusList%>

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>

							</font> <br>

							<%
								if (dto.getCityName() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=WeatherCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=WeatherCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=WeatherCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=WeatherCtl.OP_RESET%>">

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