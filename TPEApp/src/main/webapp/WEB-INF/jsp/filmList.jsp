<%@ include file="header.jsp" %>

<c:if test="${not empty directorFilterError}">
	<%@ include file="error/director-filter-warning.jsp" %>
</c:if>

<h2>Lista de pel&iacute;culas</h2>

<form class="form-inline" action="list" method="GET">
	<%@ include file="directorFilter.jsp" %>
	<%@ include file="genreList.jsp" %>
	<button type="submit" class="btn">Filtrar</button>
	<c:if test="${loggedInUser.admin}">
		<a class="btn btn-primary pull-right" type="button" href="add"><i class="icon-plus"></i> A&ntilde;adir pel&iacute;cula</a>
	</c:if>
</form>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Director</th>
		<th>Fecha de estreno</th>
		<c:if test="${loggedInUser.admin}">
			<th>Acciones</th>
		</c:if>
	</tr>
	<c:forEach items="${filmList}" var="film">
		<tr>
			<td><a href="${film.id}/details"><c:out value="${film.name}"/></a></td>
			<td><c:out value="${film.director}"/></td>
			<td><fmt:formatDate value="${film.releaseDate}" pattern="dd-MM-yyyy"/></td>
			<c:if test="${loggedInUser.admin}">
				<td>
					<form class="form-inline" action="removeFilm" method="POST">
						<input type="hidden" name="id" class="input-block-level" value="${film.id}">
						<a class="btn" type="button" href="${film.id}/edit"><i class="icon-edit"></i></a>
						<button class="btn btn-danger" type="submit"><i class="icon-remove"></i></button>
					</form>
				</td>
			</c:if>
		</tr>
	</c:forEach>
	<c:if test="${empty filmList}">
		<c:set var="cols" value="3"/>
		<c:if test="${loggedInUser.admin}">
			<c:set var="cols" value="4"/>
		</c:if>
		<c:choose>
			<c:when test="${(empty param.director) && empty param.genre}">
				<tr><td colspan="${cols}"><h4 class="muted text-center">No hay pel&iacute;culas cargadas</h4></td></tr>
			</c:when>
			<c:otherwise>
				<tr><td colspan="${cols}"><h4 class="muted text-center">No se encontraron pel&iacute;culas que respondan a ese criterio de b&uacute;squeda</h4></td></tr>
			</c:otherwise>
		</c:choose>
	</c:if>
</table>

<%@ include file="footer.jsp" %>