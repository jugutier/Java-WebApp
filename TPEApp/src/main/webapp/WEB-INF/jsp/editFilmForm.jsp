<fieldset>

	<!-- Errors variables -->
	<c:set var="nameErrors"><form:errors path="name"/></c:set>
	<c:if test="${not empty nameErrors}">
		<c:set var="nameStatus">error</c:set>
	</c:if>

	<c:set var="releaseDateErrors"><form:errors path="releaseDate"/></c:set>
	<c:if test="${not empty releaseDateErrors}">
		<c:set var="releaseDateStatus">error</c:set>
	</c:if>

	<c:set var="directorErrors"><form:errors path="director"/></c:set>
	<c:if test="${not empty directorErrors}">
		<c:set var="directorStatus">error</c:set>
	</c:if>

	<c:set var="lengthErrors"><form:errors path="length"/></c:set>
	<c:if test="${not empty lengthErrors}">
		<c:set var="lengthStatus">error</c:set>
	</c:if>

	<c:set var="descriptionErrors"><form:errors path="description"/></c:set>
	<c:if test="${not empty descriptionErrors}">
		<c:set var="descriptionStatus">error</c:set>
	</c:if>

	<c:set var="movieImageErrors"><form:errors path="movieImage"/></c:set>
	<c:if test="${not empty movieImageErrors}">
		<c:set var="movieImageStatus">error</c:set>
	</c:if>

	<div class="control-group ${nameStatus}">
		<form:label class="control-label" for="name" path="name">T&iacute;tulo</form:label>
		<div class="controls">
			<form:input type="text" id="name" path="name"/>
			<span class="help-inline"><form:errors path="name"/></span>
		</div>
	</div>
	<div class="control-group ${releaseDateStatus}">
		<form:label class="control-label" for="releaseDate" path="">Fecha de estreno</form:label>
		<div class="controls">
			<form:input type="date" id="releaseDate" path="releaseDate"/>
			<span class="help-inline"><form:errors path="releaseDate"/></span>
		</div>
	</div>
	<div class="control-group ${directorStatus}">
		<form:label class="control-label" for="director" path="">Director</form:label>
		<div class="controls">
			<form:input type="text" id="director" path="director"/>
			<span class="help-inline"><form:errors path="director"/></span>
		</div>
	</div>
	<div class="control-group ${lengthStatus}">
		<form:label class="control-label" for="length" path="">Longitud</form:label>
		<div class="controls">
			<div class="input-append">
			<form:input type="text" id="length" path="length"/>
			<span class="add-on">minutos</span>
			<span class="help-inline"><form:errors path="length"/></span>
			</div>
		</div>
	</div>
	<div class="control-group ${movieImageStatus}">
		<form:label class="control-label" for="movieImage" path="">Foto de portada</form:label>
		<div class="controls">
			<div class="media">
				<c:if test="${not empty film.movieImage}">
					<div class="pull-left">
						<img data-src="holder.js/140x140" class="img-thumbnail film-thumbnail" alt="film-Image" src="${pageContext.request.contextPath}/bin/image/get/${film.id}" data-holder-rendered="true">
					</div>
				</c:if>
				<div class="media-body">
					<input type="file" id="movieImage" name="movieImage" accept="image/*" />
					<span class="help-inline"><form:errors path="movieImage"/></span>
					<c:if test="${not empty film.movieImage}">
						<label class="checkbox">
							<input type="checkbox" name="deleteImage"> Eliminar imagen
						</label>
					</c:if>
					<p>La foto debe ser de 100kb como m&aacute;ximo.</p>
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
	<div class="control-group ${descriptionStatus}">
		<form:label class="control-label" for="description" path="description">Resumen</form:label>
		<div class="controls">
			<form:textarea id="description" class="input-xlarge" path="description" rows="5"></form:textarea>
			<span class="help-inline"><form:errors path="description"/></span>
		</div>
	</div>
	<div class="control-group">
		<div class="form-actions">
			<form:input type="hidden" path="id" />
			<button class="btn btn-primary" type="submit">Enviar</button>
		</div>
	</div>
</fieldset>