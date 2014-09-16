<select name="genre">
	<option value="">Todos los g&eacute;neros</option>
	<c:forEach items="${genreList}" var="genre">
		<option
		<c:if test="${genre == param.genre}">
			selected
		</c:if>
		><c:out value="${genre}"/></option>
	</c:forEach>
</select>