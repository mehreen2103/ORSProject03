<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.StockCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Stock View</title>
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
<form action="<%=ORSView.STOCK_CTL%>" method="post">

<jsp:useBean id="dto"
class="in.co.rays.project_3.dto.StockDTO"
scope="request"></jsp:useBean>

<div class="row pt-3 pb-3">
<div class="col-md-4"></div>

<div class="col-md-4">

<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getId() != null && dto.getId() > 0) {
%>
<h3 class="text-center">Update Stock</h3>
<%
	} else {
%>
<h3 class="text-center">Add Stock</h3>
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

<!-- Stock Name -->
<span><b>Stock Name</b><span style="color:red;">*</span></span>
<input type="text" class="form-control" name="stockName"
	placeholder="Enter Stock Name"
	value="<%=DataUtility.getStringData(dto.getStockName())%>">
<font color="red"><%=ServletUtility.getErrorMessage("stockName", request)%></font><br>

<!-- Price -->
<span><b>Price</b><span style="color:red;">*</span></span>
<input type="text" class="form-control" name="price"
	placeholder="Enter Price"
	value="<%=DataUtility.getStringData(dto.getPrice())%>">
<font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font><br>

<!-- Quantity -->
<span><b>Quantity</b><span style="color:red;">*</span></span>
<input type="text" class="form-control" name="quantity"
	placeholder="Enter Quantity"
	value="<%=DataUtility.getStringData(dto.getQuantity())%>">
<font color="red"><%=ServletUtility.getErrorMessage("quantity", request)%></font><br>

<!-- Buttons -->
<div class="text-center">
<%
	if (id > 0) {
%>
	<input type="submit" name="operation" class="btn btn-success"
		value="<%=StockCtl.OP_UPDATE%>">
	<input type="submit" name="operation" class="btn btn-warning"
		value="<%=StockCtl.OP_CANCEL%>">
<%
	} else {
%>
	<input type="submit" name="operation" class="btn btn-success"
		value="<%=StockCtl.OP_SAVE%>">
	<input type="submit" name="operation" class="btn btn-warning"
		value="<%=StockCtl.OP_RESET%>">
<%
	}
%>
</div>

</div>
</div>

</div>

<div class="col-md-4"></div>

</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>