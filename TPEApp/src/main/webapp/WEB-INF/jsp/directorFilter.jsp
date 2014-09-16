<c:choose>
	<c:when test="${empty loggedInUser}">
		<input name="director" type="text" class="input-small" placeholder="Director" disabled>
	</c:when>
	<c:otherwise>
		<input name="director" type="text" class="input-small" placeholder="Director" value="<c:out value="${param.director}"/>">
	</c:otherwise>
</c:choose>