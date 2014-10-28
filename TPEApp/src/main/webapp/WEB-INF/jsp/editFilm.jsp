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

<%@ include file="footer.jsp" %>