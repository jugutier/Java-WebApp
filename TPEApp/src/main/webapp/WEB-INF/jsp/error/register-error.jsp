
<div class="alert alert-error">
	<ul>
	<c:forEach items="${errors}" var="error">
	<li>
		<c:choose>
			<c:when test="${error=='NoPass'}">
				No ingreso una contrase&ntilde;a valida.
			</c:when>
			<c:when test="${error=='NoCoincidence'}">
				No coinciden las contrase&ntilde;as ingresadas.
			</c:when>
			<c:when test="${error=='NoMail'}">
				No ingreso email
			</c:when>
			<c:when test="${error=='MailUsed'}">
				El email ya esta en uso
			</c:when>
			<c:when test="${error=='InvalidMail'}">
				No ingreso un email valido.
			</c:when>
			<c:when test="${error=='NoName'}">
				No ingreso nombre.
			</c:when>
			<c:when test="${error=='NoLastname'}">
				No ingreso apellido.
			</c:when>
			<c:when test="${error=='WrongDate'}">
				La fecha que ingreso no es valida.(Revise el formato)
			</c:when>
			<c:when test="${error=='NoSQ'}">
				No ingreso una pregunta secreta.
			</c:when>
			<c:when test="${error=='NoSA'}">
				No ingreso respuesta secreta.
			</c:when>
		</c:choose>
		</li>
	</c:forEach>
	</ul>
</div>