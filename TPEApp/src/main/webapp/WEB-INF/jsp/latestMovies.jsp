<h2>Las 5 pel&iacute;culas m&aacute;s recientes</h2>

<table class="table table-bordered">
	<tr>
		<th>Fecha de alta</th>
		<th>Fecha de estreno</th>
		<th>Nombre</th>
		<th># Comentarios</th>
	</tr>
	<c:forEach items="${latest}" var="film">
		<c:url value="filmDetail" var="detailUrl">
			<c:param name="id" value="${film.id}" />
		</c:url>
		<tr>
			<td><c:out value="${film.creationDate}"/></td>
			<td><c:out value="${film.releaseDate}"/></td>
			<td><a href="${detailUrl}"><c:out value="${film.name}"/></a></td>
			<td><c:out value="${film.commentsCount}"/></td>
		</tr>
	</c:forEach>
	<c:if test="${empty latest}">
		<tr><td colspan="4"><h4 class="muted text-center">No hay pel&iacute;culas cargadas</h4></td></tr>
	</c:if>
</table>