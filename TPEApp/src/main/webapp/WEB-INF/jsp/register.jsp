<%@ include file="header.jsp" %>
<c:if test="${not empty errors}">
	<%@ include file="error/register-error.jsp" %>
</c:if>

<form:form class="form-signin" action="register" method="POST" commandName="userForm">
	<h2 class="form-signin-heading">Registrese gratis!</h2>
	<form:errors path="*"/>
	<form:input path="email" class="input-block-level" placeholder="Direccion de email" />
	<form:password path="password" class="input-block-level" placeholder="Contrase&ntilde;a" />
	<form:password path="passwordConfirm" class="input-block-level" placeholder="Confirmar Contrase&ntilde;a" />
	<form:input path="name" class="input-block-level" placeholder="Nombre" />
	<form:input path="lastname" class="input-block-level" placeholder="Apellido" />
	<form:input path="secretQuestion" class="input-block-level" placeholder="Pregunta secreta" />
	<form:input path="secretAnswer" class="input-block-level" placeholder="Respuesta secreta" />
	<form:input path="birthdate" type="date" class="input-block-level" placeholder="Fecha de nacimiento" />
	<button class="btn btn-large btn-primary" type="submit">Registrarse</button>
</form:form>

<%@ include file="footer.jsp" %>
	
	