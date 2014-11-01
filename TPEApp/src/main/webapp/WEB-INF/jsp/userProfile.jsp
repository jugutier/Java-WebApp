<%@ include file="header.jsp" %>

<table class="table table-bordered">
	<tr>
		<th>Comentario</th>
		<th>Fecha</th>
		<th>Pelicula</th>
	</tr>

	<c:forEach items="${comments}" var="comment">
		<c:url value="../film/filmDetails" var="detailUrl">
			<c:param name="id" value="${comment.film.id}" />
		</c:url>
		<tr>
			<td><c:out value="${comment.text}"/></td>
			<td><fmt:formatDate value="${comment.creationDate}" pattern="dd-MM-yyyy"/></td>
			<td><a href="${detailUrl}"><c:out value="${comment.film.name}"/></a></td>
		</tr>
	</c:forEach>
</table>
			
		


<%@ include file="footer.jsp" %>