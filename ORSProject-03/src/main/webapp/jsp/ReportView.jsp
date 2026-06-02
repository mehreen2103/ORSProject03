<%@page import="in.co.rays.project_3.controller.ReportCtl"%>
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

<title>Report View</title>

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

		<form action="<%=ORSView.REPORT_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ReportDTO"
				scope="request">
			</jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								if (dto.getReportType() != null && dto.getId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">UPDATE
								REPORT</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">ADD REPORT
							</h3>

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

							<!-- Report Type -->

							<span class="pl-sm-5"> <b>Report Type</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fas fa-file-alt grey-text" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="reportType"
										placeholder="Enter Report Type"
										value="<%=DataUtility.getStringData(dto.getReportType())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("reportType", request)%>

							</font> <br>

							<!-- Generated Date -->

							<span class="pl-sm-5"> <b>Generated Date</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-calendar" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" id="udate6"  name="generatedDate" class="form-control"
										placeholder="dd/mm/yyyy" readonly="readonly"
										value="<%=DataUtility.getDateString(dto.getGeneratedDate())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("generatedDate", request)%>

							</font> <br>

							<!-- Remarks -->

							<span class="pl-sm-5"> <b>Remarks</b> <span
								style="color: red;">*</span>
							</span> <br>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">

											<i class="fa fa-comment" style="font-size: 1rem;"></i>

										</div>

									</div>

									<input type="text" class="form-control" name="remarks"
										placeholder="Enter Remarks"
										value="<%=DataUtility.getStringData(dto.getRemarks())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("remarks", request)%>

							</font> <br>

							<%
								if (dto.getReportType() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=ReportCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=ReportCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=ReportCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=ReportCtl.OP_RESET%>">

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