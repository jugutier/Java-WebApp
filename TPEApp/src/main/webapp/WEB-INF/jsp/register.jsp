<%@ include file="header.jsp" %>

<c:if test="${param.status == 'auth_fail'}"> 
	<div class="alert alert-error">
		I am error.
	</div>
</c:if>

<form class="form-signin" action="registerUser" method="POST">
	<h2 class="form-signin-heading">Registrese gratis!</h2>
	<input type="text" name="email" class="input-block-level" placeholder="Direccion de email">
	<input type="password" name="password" class="input-block-level" placeholder="Contrase&ntilde;a">
	<input type="password" name="passwordConfirm" class="input-block-level" placeholder="Repita su contrase&ntilde;a">
	<input type="text" name="name" class="input-block-level" placeholder="Nombre">
	<input type="text" name="lastname" class="input-block-level" placeholder="Apellido">
	<input type="date" name="birthdate" class="input-block-level" placeholder="Fecha de nacimiento (yyyy-mm-dd)">
	<input type="text" name="secretQuestion" class="input-block-level" placeholder="Pregunta secreta">
	<input type="text" name="secretAnswer" class="input-block-level" placeholder="Respuesta secreta">
	<button class="btn btn-large btn-primary" type="submit">Registrarse</button>
</form>

<%@ include file="footer.jsp" %>