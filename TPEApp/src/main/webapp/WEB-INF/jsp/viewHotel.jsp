<%@ include file="header.jsp" %>

<a href="logoutUser">Cerrar sesi&oacute;n</a>

<h2><c:out value="${hotel.code}"/> - <c:out value="${hotel.name}"/></h2>
<h3>Descripci&oacute;n</h3>
<p><c:out value="${hotel.description}"/></p>
<h3>Comentarios</h3>
<c:if test="${empty commentList}">
	<p>No hay comentarios acerca de este hotel</p>
</c:if>
<c:forEach items="${commentList}" var="comment">
	<strong><c:out value="${comment.username}"/></strong> (<c:out value="${comment.email}"/>):
	<p><c:out value="${comment.message}"/></p>
</c:forEach>

<form action="addComment" method="POST">
	<h3>Comenta sobre este hotel</h3>
	<input type="hidden" name="hotel_id" value="<c:out value="${hotel.code}"/>"></input>
	<div class="control-group">
		<textarea name="message" rows="3"></textarea>
	</div>
	<div class="control-group">
		<button type="submit" class="btn btn-primary">Comentar</button>
	</div>
</form>


<%@ include file="footer.jsp" %>