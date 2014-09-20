<div class="alert alert-error">
	<ul>
	<c:forEach items="${errors}" var="error">
	<li>
		<c:choose>
			<c:when test="${error=='invalidMail'}">
				No ingres&oacute; un email v&aacute;lido.
			</c:when>
			<c:when test="${error=='noCoincidence'}">
				No coinciden las contrase&ntilde;as ingresadas.
			</c:when>
			<c:when test="${error=='noMail'}">
				No ingres&oacute; email.
			</c:when>
			<c:when test="${error=='wrongMail'}">
				No existe el email ingresado.
			</c:when>
			<c:when test="${error=='wrongAnswer'}">
				Su respuesta es incorrecta.
			</c:when>
			<c:when test="${error=='noPassword'}">
				No ingres&oacute; su contrase&ntilde;a.
			</c:when>
			<c:when test="${error=='noPasswordConfirm'}">
				No confirm&oacute; su contrase&ntilde;a.
			</c:when>
			<c:when test="${error=='noAnswer'}">
				No contest&oacute; su pregunta secreta.
			</c:when>
		</c:choose>
		</li>
	</c:forEach>
	</ul>
</div>