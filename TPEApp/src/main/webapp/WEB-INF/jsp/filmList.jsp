<%@ include file="header.jsp" %>

<c:if test="${not empty directorFilterError}">
	<%@ include file="error/director-filter-warning.jsp" %>
</c:if>

<h2>Lista de pel&iacute;culas</h2>

<form class="form-inline" action="filmList" method="GET">
	<%@ include file="directorFilter.jsp" %>
	<%@ include file="genreList.jsp" %>
	<button type="submit" class="btn">Filtrar</button>
</form>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Director</th>
		<th>Fecha de estreno</th>
		<th>Acciones</th>
	</tr>
	<c:forEach items="${filmList}" var="film">
		<c:url value="filmDetails" var="detailUrl">
			<c:param name="id" value="${film.id}" />
		</c:url>
		<tr>
			<td><a href="${detailUrl}"><c:out value="${film.name}"/></a></td>
			<td><c:out value="${film.director}"/></td>
			<td><fmt:formatDate value="${film.releaseDate}" pattern="dd-MM-yyyy"/></td>
			<td>
				<a class="btn" type="button"><i class="icon-edit"></i></a>
				<a class="btn btn-danger" type="button"><i class="icon-remove"></i></a>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${empty filmList}">
		<c:choose>
			<c:when test="${(empty param.director) && empty param.genre}">
				<tr><td colspan="3"><h4 class="muted text-center">No hay pel&iacute;culas cargadas</h4></td></tr>
			</c:when>
			<c:otherwise>
				<tr><td colspan="3"><h4 class="muted text-center">No se encontraron pel&iacute;culas que respondan a ese criterio de b&uacute;squeda</h4></td></tr>
			</c:otherwise>
		</c:choose>
	</c:if>
</table>

<%@ include file="footer.jsp" %>