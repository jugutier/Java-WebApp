<h2>Top 5 de pel&iacute;culas seg&uacute;n otros usuarios</h2>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Director</th>
		<th>Fecha de estreno</th>
		<th>Calificaci&oacute;n</th>
	</tr>
	<c:forEach items="${topfive}" var="film">
		<tr>
			<td><a href="${film.id}/details"><c:out value="${film.name}"/></a></td>
			<td><c:out value="${film.director}"/></td>
			<td><fmt:formatDate value="${film.releaseDate}" pattern="dd-MM-yyyy"/></td>
			<td>
				<c:forEach begin="1" end="${film.score}" var="i">  
					<i class="icon-star"></i>
				</c:forEach>
				<c:forEach begin="${film.score + 1}" end="5" var="i">  
					<i class="icon-star-empty"></i>
				</c:forEach>
				(<fmt:formatNumber value="${film.score}" maxFractionDigits="2" />)
			</td>
		</tr>

	</c:forEach>
	<c:if test="${empty topfive}">
		<tr>
			<td colspan="4">
				<h4 class="muted text-center">No hay pel&iacute;culas cargadas</h4>
			</td>
		</tr>
	</c:if>
</table>