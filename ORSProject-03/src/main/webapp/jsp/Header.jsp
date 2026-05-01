\<%@page import="in.co.rays.project_3.controller.LoginCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.RoleDTO"%>
<%@page import="in.co.rays.project_3.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header</title>

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style>
.aj {
	background-image: linear-gradient(to bottom right, #D8BFD8, #2C0E3A);
	/* background-image: linear-gradient(to bottom right, white, #4f879a); */
}

.student-nav-fix .navbar-nav {
	align-items: center !important;
}

.student-nav-fix .dropdown-menu a {
	color: #000 !important;
}

.student-nav-fix .nav-link {
	color: #fff !important;
}
</style>
</head>

<body>

	<%
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		boolean userLoggedIn = userDto != null;

		String welcomeMsg = "Hi, ";
		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userDto.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>

	<nav
		class="navbar navbar-expand-lg fixed-top aj 
    <%if (userLoggedIn && userDto.getRoleId() == RoleDTO.STUDENT) {%>
        student-nav-fix
    <%}%>">

		<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"> 
		<img src="<%=ORSView.APP_CONTEXT%>/img/custom.png" height="50px">
		</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"> <i class="fa fa-bars"
				style="color: #fff; font-size: 28px;"></i>
			</span>
		</button>

		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ml-auto">

				<%
					if (userLoggedIn) {
				%>

				<%
					if (userDto.getRoleId() == RoleDTO.STUDENT) {
				%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;">Marksheet</span>
				</a>
					<div class="dropdown-menu">
						<a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"> <i
							class="fa fa-file-alt"></i> Marksheet Merit List
						</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;">User</span>
				</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>"> <i
							class="fa fa-user-tie"></i> My Profile
						</a> <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">
							<i class="fa fa-edit"></i> Change Password
						</a>
					</div></li>

				<%
					} else if (userDto.getRoleId() == RoleDTO.ADMIN) {
				%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">User</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i
							class="fa fa-user-circle"></i>Add User</a>
							 <a class="dropdown-item" href="<%=ORSView.USER_LIST_CTL%>"><i
							class="fa fa-user-friends"></i>User List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Marksheet</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
							class="fa fa-file"></i>Add Marksheet</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i class="fa fa-paste"></i>Marksheet
							List</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
							class="fa fa-file-alt"></i>Marksheet Merit List</a> <a
							class="dropdown-item" href="<%=ORSView.GET_MARKSHEET_CTL%>"><i
							class="fa fa-copy"></i>Get Marksheet</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Role</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>"><i
							class="fa fa-user-tie"></i>Add Role</a> <a class="dropdown-item"
							href="<%=ORSView.ROLE_LIST_CTL%>"><i
							class="fa fa-user-friends"></i>Role List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">College</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>"><i
							class="fa fa-university"></i>Add College</a> <a class="dropdown-item"
							href="<%=ORSView.COLLEGE_LIST_CTL%>"><i
							class="fa fa-building"></i>College List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Course</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>"><i
							class="fa fa-book-open"></i>Add Course</a> <a class="dropdown-item"
							href="<%=ORSView.COURSE_LIST_CTL%>"><i
							class="fa fa-sort-amount-down"></i>Course List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Student</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>"><i
							class="fa fa-user-circle"></i>Add Student</a> <a
							class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>"><i
							class="fa fa-users"></i>Student List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Faculty</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>"><i
							class="fa fa-user-tie"></i>Add Faculty</a> <a class="dropdown-item"
							href="<%=ORSView.FACULTY_LIST_CTL%>"><i class="fa fa-users"></i>Faculty
							List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Time Table</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.TIMETABLE_CTL%>"><i
							class="fa fa-clock"></i>Add TimeTable</a> <a class="dropdown-item"
							href="<%=ORSView.TIMETABLE_LIST_CTL%>"><i class="fa fa-clock"></i>TimeTable
							List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Subject</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>"><i
							class="fa fa-calculator"></i>Add Subject</a> <a class="dropdown-item"
							href="<%=ORSView.SUBJECT_LIST_CTL%>"><i
							class="fa fa-sort-amount-down"></i>Subject List</a>
					</div></li>
				<!-- usercase -->
<!--                         <li class="nav-item dropdown"><a class="nav-link dropdown-toggle" -->
<!--                         href="#" data-toggle="dropdown" style="color:white;">Profile</a> -->
<!--                         <div class="dropdown-menu"> -->
<%--                             <a class="dropdown-item" href="<%=ORSView.PROFILE_CTL%>"><i class="fa fa-calculator"></i>Add profile</a> --%>
<%--                             <a class="dropdown-item" href="<%=ORSView.PROFILE_LIST_CTL%>"><i class="fa fa-sort-amount-down"></i>profile List</a> --%>
<!--                         </div></li>  -->
					
<!-- 					<li class="nav-item dropdown"> -->
<!-- 					<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;">Broker</a> -->

<!-- 	                <div class="dropdown-menu"> -->
<%-- 		              <a class="dropdown-item" href="<%=ORSView.BROKER_CTL%>"> --%>
<!-- 			           <i class="fa fa-user-tie"></i> Add Broker</a>  -->
<%-- 		              <a class="dropdown-item" href="<%=ORSView.BROKER_LIST_CTL%>"> --%>
<!-- 			               <i class="fa fa-list"></i> Broker List</a> -->
<!-- 	                </div></li> -->
	                
<!-- 	                <li class="nav-item dropdown"> -->
<!-- 	                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;">Photographer</a> -->

<!--                        <div class="dropdown-menu"> -->
<%--                          <a class="dropdown-item" href="<%=ORSView.PHOTOGRAPHER_CTL%>"> --%>
<!--                          <i class="fa fa-camera"></i> Add Photographer</a>  -->
<%--                          <a class="dropdown-item" href="<%=ORSView.PHOTOGRAPHER_LIST_CTL%>"> --%>
<!--                          <i class="fa fa-list"></i> Photographer List</a> -->
<!--                         </div> -->
<!--                    </li> -->
                   
<!--                    <li class="nav-item dropdown"> -->
<!--                           <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;">Dashboard</a> -->

<!--                           <div class="dropdown-menu"> -->
<%--                               <a class="dropdown-item" href="<%=ORSView.DASHBOARD_CTL%>"> --%>
<!--                               <i class="fa fa-tachometer-alt"></i> Add Dashboard</a>  -->

<%--                               <a class="dropdown-item" href="<%=ORSView.DASHBOARD_LIST_CTL%>"> --%>
<!--                               <i class="fa fa-list"></i> Dashboard List</a> -->
<!--                           </div> -->
<!--                     </li> -->
                    
<!--                     <li class="nav-item dropdown"> -->
<!--                               <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Build</a> -->

<!--                               <div class="dropdown-menu"> -->
<%--                                    <a class="dropdown-item" href="<%=ORSView.BUILD_CTL%>"> --%>
<!--                                    <i class="fa fa-cogs"></i> Add Build</a> -->

<%--                                    <a class="dropdown-item" href="<%=ORSView.BUILD_LIST_CTL%>"> --%>
<!--                                    <i class="fa fa-list"></i> Build List</a> -->
<!--                                </div> -->
<!--                     </li> -->
<!--                     <li class="nav-item dropdown"> -->
<!--                             <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Media</a> -->

<!--                             <div class="dropdown-menu"> -->
<%--                                  <a class="dropdown-item" href="<%=ORSView.MEDIA_CTL%>"> --%>
<!--                                  <i class="fa fa-photo-video"></i> Add Media</a> -->

<%--                                  <a class="dropdown-item" href="<%=ORSView.MEDIA_LIST_CTL%>"> --%>
<!--                                  <i class="fa fa-list"></i> Media List</a> -->
<!--                             </div> -->
<!--                     </li> -->
                    
<!--                     <li class="nav-item dropdown"> -->
<!--                             <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Data Import</a> -->

<!--                             <div class="dropdown-menu"> -->
<%--                                  <a class="dropdown-item" href="<%=ORSView.DATAIMPORT_CTL%>"> --%>
<!--                                  <i class="fa fa-file-import"></i> Add Data Import</a> -->

<%--                                  <a class="dropdown-item" href="<%=ORSView.DATAIMPORT_LIST_CTL%>"> --%>
<!--                                  <i class="fa fa-list"></i> Data Import List</a> -->
<!--                             </div> -->
<!--                     </li> -->
                    
                    <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Device</a>

                         <div class="dropdown-menu">
                              <a class="dropdown-item" href="<%=ORSView.DEVICE_CTL%>">
                              <i class="fa fa-mobile-alt"></i> Add Device</a>

                              <a class="dropdown-item" href="<%=ORSView.DEVICE_LIST_CTL%>">
                              <i class="fa fa-list"></i> Device List</a>
                         </div>
                     </li>
<!--                      <li class="nav-item dropdown"> -->
<!--                              <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Broadcast</a> -->

<!--                                <div class="dropdown-menu"> -->
<%--                                     <a class="dropdown-item" href="<%=ORSView.BROADCAST_CTL%>"> --%>
<!--                                     <i class="fa fa-bullhorn"></i> Add Broadcast</a> -->

<%--                                     <a class="dropdown-item" href="<%=ORSView.BROADCAST_LIST_CTL%>"> --%>
<!--                                     <i class="fa fa-list"></i> Broadcast List</a> -->
<!--                                </div> -->
<!--                      </li> -->
                     
                     <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> GeoFence</a>

                                <div class="dropdown-menu">
                                      <a class="dropdown-item" href="<%=ORSView.GEOFENCE_CTL%>">
                                        <i class="fa fa-map-marker-alt"></i> Add GeoFence
                                       </a>

                                      <a class="dropdown-item" href="<%=ORSView.GEOFENCE_LIST_CTL%>">
                                      <i class="fa fa-list"></i> GeoFence List
                                       </a>
                                </div>
                     </li>
                     
                     <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Cache</a>

                               <div class="dropdown-menu">
                                  <a class="dropdown-item" href="<%=ORSView.CACHE_CTL%>">
                                      <i class="fa fa-database"></i> Add Cache
                                     </a>

                                   <a class="dropdown-item" href="<%=ORSView.CACHE_LIST_CTL%>">
                                    <i class="fa fa-list"></i> Cache List
                                  </a>
                                </div>
                      </li>
                      
                      <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" style="color: white;"> Stock</a>

                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="<%=ORSView.STOCK_CTL%>">
                                    <i class="fa fa-box"></i> Add Stock </a>

                                   <a class="dropdown-item" href="<%=ORSView.STOCK_LIST_CTL%>">
                                    <i class="fa fa-list"></i> Stock List </a>
                                 </div>
                        </li>

				<%
					}
				%>

				<%
					}
				%>

				<!-- ✅ WELCOME DROPDOWN -->
				<li class="nav-item dropdown ml-3">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;"><b><%=welcomeMsg%></b></span>
				</a>
					<div class="dropdown-menu dropdown-menu-right">
						<%
							if (userLoggedIn) {
						%>
						<a class="dropdown-item"
							href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
							<i class="fa fa-sign-out-alt"></i> Logout
						</a> <a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>">
							<i class="fa fa-user-tie"></i> My Profile
						</a> <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">
							<i class="fa fa-edit"></i> Change Password
						</a> <a class="dropdown-item" target="blank"
							href="<%=ORSView.JAVA_DOC_VIEW%>"> <i class="fa fa-clone"></i>
							Java Doc
						</a>
						<%
							} else {
						%>
						<a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"> <i
							class="fa fa-sign-in-alt"></i> Login
						</a> <a class="dropdown-item"
							href="<%=ORSView.USER_REGISTRATION_CTL%>"> <i
							class="fa fa-registered"></i> User Registration
						</a>
						<%
							}
						%>
					</div></li>


			</ul>
		</div>
	</nav>

</body>
</html>
