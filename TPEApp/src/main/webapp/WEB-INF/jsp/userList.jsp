<%@ include file="header.jsp" %>
<h2>Usuarios</h2>

	<c:forEach items="${allUsers}" var="user">
		<c:url value="profile" var="detailUrl">
			<c:param name="id" value="${user.id}" />
		</c:url>
		<div class="user-list-item">
			<div class="media">
				<div class="pull-left">
					<!-- INSERT POSSIBLE AVATAR HERE -->
				</div>
				<div class="media-body">
					<a href="${detailUrl}">
						<h4 class="media-heading">
							<c:out value="${user.name}"/> <c:out value="${user.lastname}"/>
							<c:if test="${user.admin}">
								<span class="label label-important">Admin</span>
							</c:if>
							<c:if test="${user.vip}">
								<span class="label label-success">V.I.P.</span>
							</c:if>
						</h4>
					</a>
					<span class="muted"><c:out value="${user.email}"/></span>
				</div>
			</div>
		</div>
	</c:forEach>

	<c:if test="${empty allUsers}">
		<h4 class="muted">No hay usuarios registrados</h4>
	</c:if>
	
<%@ include file="footer.jsp" %>
