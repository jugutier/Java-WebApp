<%@ include file="header.jsp" %>

<h2>Mis comentarios</h2>

<c:forEach items="${commentList}" var="comment">
	<c:url value="filmDetails" var="detailUrl">
		<c:param name="id" value="${comment.film.id}" />
	</c:url>
	<a href="${detailUrl}"><h3><c:out value="${comment.film.name}"/></h3></a>
	<p><c:out value="${comment.message}"/></p>
</c:forEach>
<c:if test="${empty commentList}">
	<tr><td colspan="3"><h4 class="muted text-center">No has comentado acerca de ninguna pel&iacute;cula a&uacute;n</h4></td></tr>
</c:if>

<%@ include file="footer.jsp" %>