<div class="alert alert-error">
	<c:choose>
		<c:when test="${param.auth_fail == 'wrongUser'}">
			La direcci&oacute;n de e-mail o la contrase&ntilde;a son incorrectas.
		</c:when>
		<c:when test="${param.auth_fail == 'emptyFields'}">
			Debe completar los campos con su direcci&oacute;n de e-mail y su contrase&ntilde;a.
		</c:when>
	</c:choose>
</div>