<div class="alert alert-error">
	<c:choose>
		<c:when test="${error == 'NoComment'}">
			No puede ingresar un comentario vac&iacute;o.
		</c:when>
		<c:when test="${error == 'CommentLong'}">
			El comentario es muy largo. No puede superar los 140 caracteres.
		</c:when>
	</c:choose>
</div>