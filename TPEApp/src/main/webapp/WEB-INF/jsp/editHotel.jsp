<%@ include file="header.jsp" %>

<a href="logoutUser">Cerrar sesi&oacute;n</a>

<h2>Editar hotel</h2>

<form action="<c:out value="${action}"/>" method="POST" class="form-horizontal">
	<input type="hidden" name="hotel_id" value="<c:out value="${hotel.code}"/>"></input>
	<div class="control-group">
		<label class="control-label" for="inputName">Nombre</label>
		<div class="controls">
			<input type="text" name="hotel_name" id="inputName" value="<c:out value="${hotel.name}"/>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputDescription">Descripci&oacute;n</label>
		<div class="controls">
			<textarea name="hotel_description" id="inputDescription"><c:out value="${hotel.description}"/></textarea>
		</div>
	</div>
	<div class="control-group">
		<button type="submit" class="btn btn-success"><i class="icon-ok"></i> Guardar cambios</button>
	</div>
</form>


<%@ include file="footer.jsp" %>