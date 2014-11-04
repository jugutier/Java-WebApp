<%@ include file="header.jsp" %>

<h2>Nueva pel&iacute;cula</h2>

<form:form class="form-horizontal" action="add" method="POST" commandName="filmForm" enctype="multipart/form-data">
	<%@ include file="editFilmForm.jsp" %>
</form:form>

<%@ include file="footer.jsp" %>