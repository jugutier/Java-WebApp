<form class="login-form-mini dropdown-menu" action="../user/authenticateUser" method="POST">
	<input type="hidden" name="fromPage" class="input-block-level" value="${fromPage}">
	<input type="text" name="email" class="input-block-level" placeholder="Direcci&oacute;n de email">
	<input type="password" name="password" class="input-block-level" placeholder="Contrase&ntilde;a">
	<p class="pull-right"><a href="resetPassword">&iquest;Olvid&oacute; su contrase&ntilde;a?</a></p>
	<p class="pull-right"><a href="register">Registrarse</a></p>
	<button class="btn btn-primary pull-right" type="submit">Iniciar sesi&oacute;n</button>
</form>