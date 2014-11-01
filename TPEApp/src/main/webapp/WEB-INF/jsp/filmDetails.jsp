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
		<c:if test="${loggedInUser.admin}">
			<a class="btn btn-primary" type="button" href="editFilm"><i class="icon-edit"></i> Editar</a>
			<a class="btn btn-danger" type="submit"><i class="icon-remove"></i> Eliminar</a>
		</c:if>
		<div class="media">
			<div class="pull-left">
				<img data-src="holder.js/140x140" class="img-thumbnail" alt="film-Image" src="../image/get/${film.id}" data-holder-rendered="true" style="width: 140px; height: 140px;">
			</div>
			<div class="media-body">
				<h4 class="media-heading">Detalles</h4>

				<ul class="details-list">
					<li><h5 class="details-item-header">Director: </h5><c:out value="${film.director}"/></li>
					<li><h5 class="details-item-header">G&eacute;neros: </h5><c:out value="${film.genres}"/></li>
					<li><h5 class="details-item-header">Fecha de estreno: </h5><fmt:formatDate value="${film.releaseDate}" pattern="dd-MM-yyyy"/></li>
					<li><h5 class="details-item-header">Duraci&oacute;n: </h5><c:out value="${film.length}"/> minutos</li>
				</ul>
			</div>
		</div>

		<h3>Resumen</h3>

		<p><c:out value="${film.description}"/></p>
		
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
