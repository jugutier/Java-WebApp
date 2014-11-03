<%@ include file="header.jsp" %>

<h2>Nueva pel&iacute;cula</h2>

<form:form class="form-horizontal" action="edit" method="POST" commandName="filmForm" enctype="multipart/form-data" accept-charset="utf-8">
	<%@ include file="editFilmForm.jsp" %>
</form:form>

<%@ include file="footer.jsp" %>