<%@ include file="header.jsp" %>

<c:if test="${param.status == 'auth_fail'}"> 
	<div class="alert alert-error">
		I am error.
	</div>
</c:if>

<%@ include file="topfive.jsp" %>

<%@ include file="footer.jsp" %>