<c:if test="${not empty followedComments}">
<h2>Comentarios mas recientes de usuarios que sigues</h2>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Usuario</th>
		<th>Comentario</th>
		<th>Fecha de comentario</th>
	</tr>
	<c:forEach items="${followedComments}" var="comment">
		<tr>
			<td><c:out value="${comment.user.name}"/></td>
			<td><c:out value="${comment.user.email}"/></td>
			<td><c:out value="${comment.text}"/></td>
			<td><c:out value="${comment.creationDate}"/></td>
		</tr>
	</c:forEach>
</table>
</c:if>