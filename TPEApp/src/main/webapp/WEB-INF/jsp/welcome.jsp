<%@ include file="header.jsp" %>

<c:if test="${param.status == 'auth_fail'}"> 
	<div class="alert alert-error">
		I am error.
	</div>
</c:if>

<form class="form-signin" action="authenticateUser" method="POST">
	<h2 class="form-signin-heading">Identif&iacute;quese</h2>
	<input type="text" name="email" class="input-block-level" placeholder="Direccion de email">
	<input type="password" name="password" class="input-block-level" placeholder="Contrase&ntilde;a">
	<button class="btn btn-large btn-primary" type="submit">Iniciar sesi&oacute;n</button>
</form>
<%@ include file="topfive.jsp" %>

<%@ include file="latestMovies.jsp" %>

<%@ include file="footer.jsp" %>