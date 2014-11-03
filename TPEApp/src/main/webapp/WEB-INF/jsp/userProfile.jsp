<%@ include file="header.jsp" %>

<div class="media">
	<div class="pull-left">
		<!-- INSERT POSSIBLE AVATAR HERE -->
	</div>
	<div class="media-body">
		<a href="${detailUrl}">
			<h2 class="media-heading">
				<c:out value="${user.name}"/> <c:out value="${user.lastname}"/>
				<c:if test="${user.admin}">
					<span class="label label-important">Admin</span>
				</c:if>
				<c:if test="${user.vip}">
					<span class="label label-success">V.I.P.</span>
				</c:if>
			</h2>
		</a>
		<h4 class="muted"><c:out value="${user.email}"/></h4>
		<form action="${pageContext.request.contextPath}/bin/user/follow" method="POST">
			<input type="hidden" name="fromPage" value="${fromPage}" />
			<input type="hidden" name="id" value="${user.id}"/>
			<button type="submit" class="btn">Seguir a <c:out value="${user.name}"/></button>
		</form>
		<form action="${pageContext.request.contextPath}/bin/user/unFollow" method="POST">
			<input type="hidden" name="fromPage" value="${fromPage}" />
			<input type="hidden" name="id" value="${user.id}"/>
			<button type="submit" class="btn">Dejar de seguir a <c:out value="${user.name}"/></button>
		</form>
		<h5 class="details-item-header">Fecha de nacimiento: </h5><fmt:formatDate value="${user.birthdate}" pattern="dd-MM-yyyy"/>
	</div>
</div>

<hr>

<h3>Comentarios</h3>

<c:forEach items="${comments}" var="comment">
	<div class="user-list-item">
		<h4><a href="${pageContext.request.contextPath}/bin/film/${comment.film.id}/details"><c:out value="${comment.film.name}"/></a></h4>
		<p class="muted"><fmt:formatDate value="${comment.creationDate}" pattern="dd-MM-yyyy"/></p>
		<p><c:out value="${comment.text}"/></p>
	</div>
</c:forEach>

<c:if test="${empty comments}">
	<h4 class="muted"><c:out value="${user.name}"/> a&uacute;n no ha comentado acerca de ninguna pel&iacute;cula</h4>
</c:if>

<%@ include file="footer.jsp" %>