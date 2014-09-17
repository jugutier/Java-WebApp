<%@ include file="header.jsp" %>

<h2>Recuperar contrase&ntilde;a</h2>

<c:choose>
	<c:when test="${stage == 'getEmail'}">
		<form class="form-signin" action="resetPassword" method="POST">
			<input type="text" name="email" class="input-block-level" placeholder="Direccion de email">
			<button class="btn btn-large btn-primary" type="submit">Siguiente</button>
		</form>
	</c:when>
	<c:when test="${stage == 'question'}">
		<form class="form-signin" action="resetPasswordAction" method="POST">
			<h4><c:out value="${secretQuestion}"/></h4>
			<input type="hidden" name="email" class="input-block-level" value="<c:out value="${email}"/>">
			<input type="text" name="answer" class="input-block-level" placeholder="Respuesta">
			<input type="password" name="password" class="input-block-level" placeholder="Contrase&ntilde;a nueva">
			<input type="password" name="passwordConfirm" class="input-block-level" placeholder="Repita su contrase&ntilde;a nueva">
			<button class="btn btn-large btn-primary" type="submit">Restablecer contrase&ntilde;a</button>
		</form>
	</c:when>
</c:choose>

<%@ include file="footer.jsp" %>