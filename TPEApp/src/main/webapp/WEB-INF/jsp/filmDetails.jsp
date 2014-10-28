<%@ include file="header.jsp" %>

<c:choose>
	<c:when test="${empty film}">
		<%@ include file="error/film-not-found-error.jsp" %>
	</c:when>
	<c:otherwise>
		<c:if test="${not empty error}">
			<%@ include file="error/comment-error.jsp" %>
		</c:if>
		<h2><c:out value="${film.name}"/></h2>
		
		<dl class="dl-horizontal">
			<dt>Resumen</dt>
			<dd><c:out value="${film.description}"/></dd>
			<dt>Director</dt>
			<dd><c:out value="${film.director}"/></dd>
			<dt>G&eacute;nero</dt>
			<dd><c:out value="${film.genre}"/></dd>
			<dt>Fecha de estreno</dt>
			<dd><fmt:formatDate value="${film.releaseDate}" pattern="dd-MM-yyyy"/></dd>
			<dt>Duraci&oacute;n</dt>
			<dd><c:out value="${film.length}"/> minutos</dd>
		</dl>
		
		<h3>Comentarios</h3>
		
		<%@ include file="commentList.jsp" %>
		
		<c:if test="${empty commentList}">
			<h4 class="muted">No hay comentarios acerca de esta pel&iacute;cula</h4>
		</c:if>
		<hr/>
		<c:choose>
			<c:when test="${empty loggedInUser}">
				<h4 class="muted">Debe iniciar sesi&oacute;n para comentar acerca de esta pel&iacute;cula</h4>
			</c:when>
			<c:when test="${!userCanComment}">
				<h4 class="muted">No puede comentar</h4>
			</c:when>
			<c:otherwise>
				<%@ include file="newComment.jsp" %>
			</c:otherwise>
		</c:choose>

	</c:otherwise>
</c:choose>

<%@ include file="footer.jsp" %>
