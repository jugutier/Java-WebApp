<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<head>
		<title>El TPE de PAW</title>

		<link href="css/bootstrap.css" rel="stylesheet">
		<link href="css/bootstrap-responsive.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
		<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
		<link rel="shortcut icon" href="../assets/ico/favicon.png">
	</head>
	<body>

	<div class="container-narrow">
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-narrow">
					<a class="brand" href="home">El TPE de PAW</a>
					<ul class="nav pull-right">
						<c:choose>
						    <c:when test="${empty loggedInUser}">
						        <li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">Iniciar sesi&oacute;n <b class="caret"></b></a>
									<%@ include file="loginFormMini.jsp" %>
								</li>
						    </c:when>
						    <c:otherwise>
						       <li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="${loggedInUser.name}"/> <b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a href="userComments">Ver mis comentarios</a></li>
										<li class="divider"></li>
										<li><a href="logout">Cerrar sesi&oacute;n</a></li>
									</ul>
								</li>
						    </c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</div>
		<c:if test="${not empty param.auth_fail}">
			<%@ include file="error/login-error.jsp" %>
		</c:if>

		