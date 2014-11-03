<form:form class="form-comment form-horizontal" action="details" method="POST" commandName="commentForm">
	<h3 class="form-comment-heading">Nuevo comentario</h3>
	<p>
		<label for="rating">Puntuaci&oacute;n:</label>
		<form:select class="span1" path="rating">
			<c:forEach begin="0" end="5" var="i">
				<option <c:if test="${i == param.rating}"> selected </c:if> >
					<c:out value="${i}"/>
				</option>
			</c:forEach>
		</form:select>
	</p>
	<p> 
		<form:textarea path="text" class="input-block-level" placeholder="Comentario" />
		<form:errors path="text" />	
	</p>
	<form:input type="hidden" path="filmId" value="${film.id}" />
	<input type="hidden" value="${fromPage}" name="fromPage" />
	<button class="btn btn-primary" type="submit">Enviar</button>
</form:form>
