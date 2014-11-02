<%@ include file="header.jsp" %>

<h2>Mis comentarios</h2>

<c:forEach items="${commentList}" var="comment">
	<c:url value="/bin/film/filmDetails" var="detailUrl">
		<c:param name="id" value="${comment.film.id}" />
	</c:url>
	<div class="user-list-item">
		<h4><a href="${detailUrl}"><c:out value="${comment.film.name}"/></a></h4>
		<p class="muted"><fmt:formatDate value="${comment.creationDate}" pattern="dd-MM-yyyy"/></p>
		<p><c:out value="${comment.text}"/></p>
	</div>
</c:forEach>

<c:if test="${empty commentList}">
	<h4 class="muted text-center">No has comentado acerca de ninguna pel&iacute;cula a&uacute;n</h4>
</c:if>

<%@ include file="footer.jsp" %>