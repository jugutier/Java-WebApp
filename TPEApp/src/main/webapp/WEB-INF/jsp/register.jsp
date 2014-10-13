<%@ include file="header.jsp" %>
<c:if test="${not empty errors}">
	<%@ include file="error/register-error.jsp" %>
</c:if>

<form class="form-signin" action="registerUser" method="POST">
	<h2 class="form-signin-heading">Registrese gratis!</h2>
	<input type="email" required name="email" class="input-block-level" placeholder="Direccion de email" value="<c:out value="${param.email}"/>" >
	<input type="password" required name="password" class="input-block-level" placeholder="Contrase&ntilde;a">
	<input type="password" required name="passwordConfirm" class="input-block-level" placeholder="Repita su contrase&ntilde;a">
	<input type="text" required name="name" class="input-block-level" placeholder="Nombre" value="<c:out value="${param.name}"/>">
	<input type="text" required name="lastname" class="input-block-level" placeholder="Apellido" value="<c:out value="${param.lastname}"/>">
	<input type="date" required name="birthdate" class="input-block-level" placeholder="Fecha de nacimiento (yyyy-mm-dd)" value="<c:out value="${param.birthdate}"/>">
	<input type="text" required name="secretQuestion" class="input-block-level" placeholder="Pregunta secreta" value="<c:out value="${param.secretQuestion}"/>">
	<input type="text" required name="secretAnswer" class="input-block-level" placeholder="Respuesta secreta" value="<c:out value="${param.secretAnswer}"/>">
	<button class="btn btn-large btn-primary" type="submit">Registrarse</button>
</form>

<%@ include file="footer.jsp" %>