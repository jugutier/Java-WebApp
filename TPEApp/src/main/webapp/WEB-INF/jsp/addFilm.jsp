<%@ include file="header.jsp" %>

<h2>Nueva pel&iacute;cula</h2>
	<form:form class="form-horizontal" action="addFromCSV" method="POST" commandName="filmsCSVForm" enctype="multipart/form-data">
		<h3> Cargar desde archivo </h3>
		<%@ include file="loadCSV.jsp" %>
	</form:form>
</fieldset>

<form:form class="form-horizontal" action="add" method="POST" commandName="filmForm" enctype="multipart/form-data">
	<h3> Cargar una pel&iacute;cula </h3>
	<%@ include file="editFilmForm.jsp" %>
</form:form>

<%@ include file="footer.jsp" %>