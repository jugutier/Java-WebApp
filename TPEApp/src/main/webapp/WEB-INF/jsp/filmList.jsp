<%@ include file="header.jsp" %>

<h2>Lista de pel&iacute;culas</h2>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Director</th>
		<th>Fecha de estreno</th>
	</tr>
	<c:forEach items="${filmList}" var="film">
		<c:url value="filmDetails" var="detailUrl">
			<c:param name="id" value="${film.id}" />
		</c:url>
		<tr>
			<td><a href="${detailUrl}"><c:out value="${film.name}"/></a></td>
			<td><c:out value="${film.director}"/></td>
			<td><c:out value="${film.releaseDate}"/></td>
		</tr>
	</c:forEach>
	<c:if test="${empty filmList}">
		<tr><td colspan="3"><h4 class="muted text-center">No hay pel&iacute;culas cargadas</h4></td></tr>
	</c:if>
</table>

<a href="addHotel" class="btn btn-primary pull-right"><i class="icon-plus"></i> A&ntilde;adir pel&iacute;cula</a>

<%@ include file="footer.jsp" %>