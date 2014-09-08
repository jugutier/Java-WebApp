<%@ include file="header.jsp" %>

<c:if test="${param.status == 'auth_fail'}"> 
	<div class="alert alert-error">
		I am error.
	</div>
</c:if>

<form class="form-signin" action="authenticateUser" method="POST">
	<h2 class="form-signin-heading">Identif&iacute;quese</h2>
	<input type="text" name="username" class="input-block-level" placeholder="Nombre de usuario">
	<input type="password" name="password" class="input-block-level" placeholder="Contrase&ntilde;a">
	<button class="btn btn-large btn-primary" type="submit">Iniciar sesi&oacute;n</button>
</form>

<%@ include file="footer.jsp" %>