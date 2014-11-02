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

<form:form class="form-horizontal" action="edit" method="POST" commandName="filmForm" modelAttribute="film" enctype="multipart/form-data" accept-charset="utf-8">
	<fieldset>
		<form:errors path="*"/>
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
			<form:label class="control-label" for="movieImage" path="">Foto de portada</form:label>
			<div class="controls">
				<div class="media">
					<c:if test="${not empty film.movieImage}">
						<div class="pull-left">
							<img data-src="holder.js/140x140" class="img-thumbnail" alt="film-Image" src="${pageContext.request.contextPath}/bin/image/get/${film.id}" data-holder-rendered="true" style="width: 140px; height: 140px;">
						</div>
					</c:if>
					<div class="media-body">
						<input type="file" id="movieImage" name="movieImage" />
						<c:if test="${not empty film.movieImage}">
							<label class="checkbox">
								<input type="checkbox"> Eliminar imagen
							</label>
						</c:if>
					</div>
				</div>				
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="genres" path="">G&eacute;neros</form:label>
			<div class="controls">
				<ul>
					<c:forEach items="${genreList}" var="genre">
						<li>
							<label class="checkbox">
								<form:checkbox path="genres" value="${genre}"/>
								<c:out value="${genre.genre}" />
							</label>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="control-group">
			<form:label class="control-label" for="description" path="">Resumen</form:label>
			<div class="controls">
				<form:textarea id="description" class="input-xlarge" path="description" rows="5"></form:textarea>
			</div>
		</div>
		<div class="control-group">
			<div class="form-actions">
				<form:input type="hidden" path="id" />
				<button class="btn btn-primary" type="submit">Enviar</button>
			</div>
		</div>
	</fieldset>
	<form:errors path="*" />
</form:form>

<%@ include file="footer.jsp" %>