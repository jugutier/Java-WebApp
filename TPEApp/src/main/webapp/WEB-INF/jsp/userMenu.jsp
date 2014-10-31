<ul class="dropdown-menu">
	<li><a href="${pageContext.request.contextPath}/bin/user/userComments">Ver mis comentarios</a></li>
	<c:if test="${loggedInUser.admin}">
		<li><a href="../user/reportList">Ver comentarios denunciados</a></li>
	</c:if>
	<li class="divider"></li>
	<li><a href="${pageContext.request.contextPath}/bin/user/logout">Cerrar sesi&oacute;n</a></li>
</ul>