<h2>Top 5 de pel&iacute;culas seg&uacute;n otros usuarios</h2>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Director</th>
		<th>Fecha de estreno</th>
		<th>Calificaci&oacute;n</th>
	</tr>
	<c:forEach items="${topfive}" var="film">
		<c:url value="filmDetail" var="detailUrl">
			<c:param name="id" value="${film.id}" />
		</c:url>
		<tr>
			<td><a href="${detailUrl}"><c:out value="${film.name}"/></a></td>
			<td><c:out value="${film.director}"/></td>
			<td><c:out value="${film.releaseDate}"/></td>
			<td>
				<span class="rating">
				  <span class="star"></span>
				  <span class="star"></span>
				  <span class="star"></span>
				  <span class="star"></span>
				  <span class="star"></span>
				</span>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${empty topfive}">
		<tr><td colspan="4"><h4 class="muted text-center">No hay pel&iacute;culas cargadas</h4></td></tr>
	</c:if>
</table>