<form class="form-comment form-horizontal" action="addComment" method="POST">
	<h3 class="form-comment-heading">Nuevo comentario</h3>
	<p>
		<label for="rating">Puntuaci&oacute;n:</label>
		<select class="span1" name="rating">
			<option value="0">0</option>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
		</select>
	</p>
	<p> 
		<textarea name="comment" class="input-block-level" placeholder="Comentario"></textarea>
	</p>
	<input type="hidden" name="id" value="${film.id}" />
	<button class="btn btn-primary" type="submit">Enviar</button>
</form>