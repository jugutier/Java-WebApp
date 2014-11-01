<%@ include file="header.jsp" %>
<h2>Usuarios</h2>

	<table class="table table-bordered">
	<tr>
		<th>Nombre</th>
	</tr>
	

	<c:forEach items="${allUsers}" var="user">
		<c:url value="profile" var="detailUrl">
			<c:param name="id" value="${user.id}" />
		</c:url>
		<tr>
			<td><a href="${detailUrl}"><c:out value="${user.name}"/></a></td>
		</tr>
	</c:forEach>
	</table>
	
<%@ include file="footer.jsp" %>
		
		