<%@ include file="header.jsp" %>

<a href="logoutUser">Cerrar sesi&oacute;n</a>

<h2 class="form-signin-heading">Lista de hoteles</h2>

<table class="table table-bordered">
	<tr>
		<th>C&oacute;digo</th>
		<th>Nombre</th>
		<th>Descripci&oacute;n</th>
		<th>Acciones</th>
	</tr>
	<c:forEach items="${hotelList}" var="hotel" varStatus="num">
		<c:url value="viewHotel" var="viewUrl">
			<c:param name="id" value="${hotel.code}" />
		</c:url>
		<c:url value="editHotel" var="editUrl">
			<c:param name="id" value="${hotel.code}" />
		</c:url>
		<c:if test="${num.count % 2 == 0}">
		<tr class="even-row">
		</c:if>
		<c:if test="${num.count % 2 != 0}">
		<tr class="uneven-row">
		</c:if>
			<td><c:out value="${hotel.code}"/></td>
			<td><a href="${viewUrl}"><c:out value="${hotel.name}"/></a></td>
			<td><c:out value="${hotel.description}"/></td>
			<td><a href="${editUrl}" class="btn btn-primary"><i class="icon-edit"></i></a></td>
		</tr>
	</c:forEach>
	<c:if test="${empty hotelList}">
		<tr><td><h4 class="muted">No hay hoteles cargados</h4></td></tr>
	</c:if>
</table>

<a href="addHotel" class="btn btn-primary"><i class="icon-plus"></i> A&ntilde;adir hotel</a>

<%@ include file="footer.jsp" %>