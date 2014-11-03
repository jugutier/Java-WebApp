<ul class="dropdown-menu">
	<li><a href="${pageContext.request.contextPath}/bin/user/userComments">Ver mis comentarios</a></li>
	<c:if test="${loggedInUser.admin}">
		<li class="divider"></li>
		<li><a href="${pageContext.request.contextPath}/bin/film/add">A&ntilde;adir pel&iacute;cula</a></li>
		<li><a href="${pageContext.request.contextPath}/bin/comment/reported">Ver denuncias</a></li>
	</c:if>
	<li class="divider"></li>
	<li><a href="${pageContext.request.contextPath}/bin/user/logout">Cerrar sesi&oacute;n</a></li>
</ul>