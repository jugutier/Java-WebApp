<%@ include file="header.jsp" %>

<h2>
<c:choose>
	<c:when test="${empty film}">
		Nueva pel&iacute;cula
	</c:when>
	<c:otherwise>
		Edici&oacute;n de pel&iacute;cula
	</c:otherwise>
</c:choose>
</h2>

<form:form class="form-horizontal" action="filmDetails?id=${film.id}" method="POST" commandName="filmForm">
	<fieldset>
		<div class="control-group">
			<form:label class="control-label" for="name" path="">T&iacute;tulo</form:label>
			<div class="controls">
				<form:input type="text" id="name" path="name"/>
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="releaseDate" path="">Fecha de estreno</form:label>
			<div class="controls">
				<form:input type="text" id="releaseDate" path="releaseDate"/>
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="director" path="">Director</form:label>
			<div class="controls">
				<form:input type="text" id="director" path="director"/>
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="length" path="">Longitud</form:label>
			<div class="controls">
				<div class="input-append">
				<form:input type="text" id="length" path="length"/>
				<span class="add-on">minutos</span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="genres" path="">G&eacute;neros</form:label>
			<div class="controls">
				<c:forEach items="${genreList}" var="genre">
					<label class="checkbox" id="genres">
						<input type="checkbox" value="">
						<c:out value="${genre.genre}" />
					</label>
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="description" path="">Resumen</form:label>
			<div class="controls">
				<textarea id="description" path="description" rows="3"></textarea>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<form:input type="hidden" path="filmId" value="${film.id}" />
				<button class="btn btn-primary" type="submit">Enviar</button>
			</div>
		</div>
	</fieldset>
	<form:errors path="*" />
</form:form>

<%@ include file="footer.jsp" %>