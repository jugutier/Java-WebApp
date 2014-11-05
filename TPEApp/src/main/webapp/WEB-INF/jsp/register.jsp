<%@ include file="header.jsp" %>

<h2 class="form-signin-heading">Registrarse</h2>

<c:if test="${not empty errors}">
	<%@ include file="error/register-error.jsp" %>
</c:if>

<form:form class="form-horizontal" action="register" method="POST" commandName="userForm">

	<!-- Errors variables -->
	<c:set var="nameErrors"><form:errors path="name"/></c:set>
	<c:if test="${not empty nameErrors}">
		<c:set var="nameStatus">error</c:set>
	</c:if>

	<c:set var="emailErrors"><form:errors path="email"/></c:set>
	<c:if test="${not empty emailErrors}">
		<c:set var="emailStatus">error</c:set>
	</c:if>

	<c:set var="passwordConfirmErrors"><form:errors path="passwordConfirm"/></c:set>
	<c:if test="${not empty passwordConfirmErrors}">
		<c:set var="passwordConfirmStatus">error</c:set>
	</c:if>

	<c:set var="passwordErrors"><form:errors path="password"/></c:set>
	<c:if test="${not empty passwordErrors}">
		<c:set var="passwordStatus">error</c:set>
	</c:if>

	<c:set var="lastnameErrors"><form:errors path="lastname"/></c:set>
	<c:if test="${not empty lastnameErrors}">
		<c:set var="lastnameStatus">error</c:set>
	</c:if>

	<c:set var="secretQuestionErrors"><form:errors path="secretQuestion"/></c:set>
	<c:if test="${not empty secretQuestionErrors}">
		<c:set var="secretQuestionStatus">error</c:set>
	</c:if>

	<c:set var="secretAnswerErrors"><form:errors path="secretAnswer"/></c:set>
	<c:if test="${not empty secretAnswerErrors}">
		<c:set var="secretAnswerStatus">error</c:set>
	</c:if>

	<c:set var="birthdateErrors"><form:errors path="birthdate"/></c:set>
	<c:if test="${not empty birthdateErrors}">
		<c:set var="birthdateStatus">error</c:set>
	</c:if>

	<fieldset>
		<div class="control-group ${emailStatus}">
			<form:label class="control-label" for="email" path="email">Direcci&oacute;n de email</form:label>
			<div class="controls">
				<form:input id="email" path="email" />
				<span class="help-inline"><form:errors path="email"/></span>
			</div>
		</div>
		<div class="control-group ${passwordStatus} ${passwordConfirmStatus}">
			<form:label class="control-label" for="password" path="password">Contrase&ntilde;a</form:label>
			<div class="controls">
				<form:password id="password" path="password" />
				<span class="help-inline"><form:errors path="password"/></span>
			</div>
		</div>
		<div class="control-group ${passwordConfirmStatus}">
			<form:label class="control-label" for="passwordConfirm" path="passwordConfirm">Confirmar contrase&ntilde;a</form:label>
			<div class="controls">
				<form:password id="passwordConfirm" path="passwordConfirm" />
				<span class="help-inline"><form:errors path="passwordConfirm"/></span>
			</div>
		</div>
		<div class="control-group ${nameStatus}">
			<form:label class="control-label" for="name" path="name">Nombre</form:label>
			<div class="controls">
				<form:input id="name" path="name" />
				<span class="help-inline"><form:errors path="name"/></span>
			</div>
		</div>
		<div class="control-group ${lastnameStatus}">
			<form:label class="control-label" for="lastname" path="lastname">Apellido</form:label>
			<div class="controls">
				<form:input id="lastname" path="lastname" />
				<span class="help-inline"><form:errors path="lastname"/></span>
			</div>
		</div>
		<div class="control-group ${secretQuestionStatus}">
			<form:label class="control-label" for="secretQuestion" path="secretQuestion">Pregunta secreta</form:label>
			<div class="controls">
				<form:input path="secretQuestion" />
				<span class="help-inline"><form:errors path="secretQuestion"/></span>
			</div>
		</div>
		<div class="control-group ${secretAnswerStatus}">
			<form:label class="control-label" for="secretAnswer" path="secretAnswer">Respuesta secreta</form:label>
			<div class="controls">
				<form:input id="secretAnswer" path="secretAnswer"/>
				<span class="help-inline"><form:errors path="secretAnswer"/></span>
			</div>
		</div>
		<div class="control-group ${birthdateStatus}">
			<form:label class="control-label" for="birthdate" path="birthdate">Fecha de nacimiento</form:label>
			<div class="controls">
				<form:input path="birthdate" id="birthdate" type="date" />
				<span class="help-inline"><form:errors path="birthdate"/></span>
			</div>
		</div>
		<button class="btn btn-large btn-primary" type="submit">Registrarse</button>
	</fieldset>
</form:form>

<%@ include file="footer.jsp" %>
	
	