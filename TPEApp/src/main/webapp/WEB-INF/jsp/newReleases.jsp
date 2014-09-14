<h2>Los estrenos de esta semana</h2>

<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
		<th>Descripci&oacute;n</th>
	</tr>
	<c:forEach items="${newReleases}" var="film">
		<c:url value="filmDetails" var="detailUrl">
			<c:param name="id" value="${film.id}" />
		</c:url>
		<tr>
			<td><a href="${detailUrl}"><c:out value="${film.name}"/></a></td>
			<td>
				<c:choose>
					<c:when test="${fn:length(film.description) <= 300}">
						<c:out value="${film.description}"/>
					</c:when>
					<c:otherwise>
						<c:out value="${fn:substring(film.description, 0, 300)}"/>
						... <a href="${detailUrl}"><small>[Ver m&aacute;s]</small></a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="4">
		<c:choose>
			<c:when test="${empty newReleases}">
				<h4 class="muted text-center">No hay estrenos esta semana</h4>
			</c:when>
			<c:otherwise>
				<a href="filmList" class="btn btn-large btn-block btn-primary" type="button">Ver el listado completo</a>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>