<form class="form-comment form-horizontal" action="addComentToFilm" method="POST">
	<h3 class="form-comment-heading">Nuevo comentario</h3>
	<p>
		<label for="rating">Puntuaci&oacute;n:</label>
		<select class="span1" name="rating">
			<c:forEach begin="0" end="5" var="i">
				<option <c:if test="${i == param.rating}"> selected </c:if> >
					<c:out value="${i}"/>
				</option>
			</c:forEach>
		</select>
	</p>
	<p> 
		<textarea name="comment" class="input-block-level" placeholder="Comentario"></textarea>
	</p>
	<input type="hidden" name="id" value="${film.id}" />
	<button class="btn btn-primary" type="submit">Enviar</button>
</form>