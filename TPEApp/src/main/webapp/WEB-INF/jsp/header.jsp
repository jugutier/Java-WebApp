<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<head>
		<title>GAJAmdb</title>
		
		<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
		<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
		<link rel="shortcut icon" href="../assets/ico/favicon.png">
		<link href="data:image/x-icon;base64,AAABAAEAEBAAAAEAIABoBAAAFgAAACgAAAAQAAAAIAAAAAEAIAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA7MzT/AAAAbgAAAC4AAAANAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMTkxP/0xKS/9LRkj/ko2N/4Fyc/93Y2T/AAAAggAAADkAAAASAAAAAwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQtpkAP//kwf//5wM//+jE///pxr//awZ/0xKT/9PS0z/R0BC/yYlJP+DcHP/AAAAlQAAAAkAAAAAAAAAACsnKv/1cQD//5AI//+eEf//qSX//708///ESP//x0r//8ZI//WDAP9nIQH/12YA/1hCKf8dGhy3AAAAAAAAAABMSEn/EQoI//+ODv//nhr//6ov//+7Sv//y1r//9Zq///gc///xkb/xlgA/9VtAP+kSwT/AAABAQAAAAAAAAAASkVG/xAJCP8QCQj/EwkI//+YJv9iJwT//8xn///igf///+z////T/+9yAP9nKQL/TEtL/wAAAAAAAAAAAAAAAEdDQ/8QCQj//2wG//+YK///pj7//7ha//+7X///cwD//+SX//+0Ov+0VAD/PRcF/0hGRv8AAAAAAAAAAAAAAAVtLQD/EAkI/6E0Av//jSz//58///+zVf//wmr//854/+ZnAP+uTAD/Uh8D/zARBv8AAAAQAAAAAAAAAAAAAAAp0j8A/xoMB//+YAr//3ki//+ONP//okb//65U//+3X///u1///7la/5tLC/8iDAb/AAAAAAAAAAAAAAAAPDIy/zYvM/8uDQT/qDEF/9RJEP/vZiD//X0u//+NOP//lz///5g8//+WNP90KgD/QkA//wAAAAAAAAAAAAAAADcyMv88NDT/8fHx/0U7O/84LSz/9/j4/4Z2df8RDhH/bC4V/9JSGP/qWQ7/6lQE/zMyM/8AAAAAAAAAAAAAAADCiHn/NjAw/zs0NP8LCAn/BgQG//n49//x7+//SD49//f08v9OQ0L/+/b1/1dKSf9nX2P/AAAAAAAAAAAAAAAAAAAAAAAAAADk4+P/Mi4u/zw0NP/59vX/FBET/xAPEP8AAAAeAAAABkk2RnP38O3/AAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMC0r/zo1M/85MC7/+/bz/ykjJv8XFRb/AAAAKQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADcvLv9EOzr/+O/s//nu7f8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA7/8AAOA/AADgAQAAwAAAAMABAADAAQAAwAEAAMADAADAAwAAgAMAAIADAACAAwAA4HcAAPwPAAD/hwAA//8AAA==" rel="icon" type="image/x-icon" />
	</head>
	<body>

	<div class="container-narrow">
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-narrow">
					<a class="brand" href="home">GAJAmdb</a>
					<ul class="nav nav-pills">
  						<li>
  							<a href="filmList" type="button">Pel&iacute;culas</a>
  						</li>
  					</ul>
					
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

		