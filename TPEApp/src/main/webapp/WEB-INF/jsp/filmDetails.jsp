<%@ include file="header.jsp" %>

<h2><c:out value="${film.name}"/></h2>


<dl class="dl-horizontal">
	<dt>Resumen</dt>
	<dd><c:out value="${film.description}"/></dd>
	<dt>Director</dt>
	<dd><c:out value="${film.director}"/></dd>
	<dt>G&eacute;nero</dt>
	<dd><c:out value="${film.genre}"/></dd>
	<dt>Fecha de estreno</dt>
	<dd><c:out value="${film.releaseDate}"/></dd>
	<dt>Duraci&oacute;n</dt>
	<dd><c:out value="${film.length}"/> minutos</dd>
</dl>

<h3>Comentarios</h3>

<c:forEach items="${commentList}" var="comment">
	<p>
		<strong><c:out value="${comment.username}"/></strong>
		<c:forEach begin="1" end="${comment.score}" var="i">  
			<i class="icon-star"></i>
		</c:forEach>
		<c:forEach begin="${comment.score + 1}" end="5" var="i">  
			<i class="icon-star-empty"></i>
		</c:forEach>
	</p>
	<p><c:out value="${comment.message}"/></p>
</c:forEach>

<%@ include file="footer.jsp" %>