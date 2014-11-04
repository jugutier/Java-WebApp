<%@ include file="header.jsp" %>

<h2>Comentarios denunciados</h2>

<c:if test="${empty commentList}">
	<h4 class="muted">No hay comentarios denunciados</h4>
</c:if>

<c:forEach items="${commentList}" var="comment">
	<div class="comment-body">
		<c:url value="/bin/comment/${comment.id}/discardReports" var="discardReportsUrl">
		</c:url>
		<form class="form-inline pull-right" action="${pageContext.request.contextPath}/bin/comment/removeComment" method="POST">
			<input type="hidden" name="fromPage" value="${fromPage}" />
			<input type="hidden" name="film" value="${comment.film.id}" />
			<input type="hidden" name="id" value="${comment.id}" />
			<button href="#" class="btn btn-danger" type="submit"><i class="icon-remove"></i></button>
		</form>
		<p>
			<strong><c:out value="${comment.user.name}"/></strong>
			<c:if test="${comment.user.admin}">
				 <span class="label label-important">Admin</span>
			</c:if>
			<c:if test="${comment.user.vip}">
				 <span class="label label-success">V.I.P.</span>
			</c:if>
		</p>
		<p>
			<c:forEach begin="1" end="${comment.filmRate}" var="i">  
				<i class="icon-star"></i>
			</c:forEach>
			<c:forEach begin="${comment.filmRate + 1}" end="5" var="i">  
				<i class="icon-star-empty"></i>
			</c:forEach>
		</p>
		<p>
			<c:out value="${comment.text}"/>
		</p>
	</div>
	<div class="comment-controls">
		<p class="text-right">
			Denuncias: <span class="badge badge-important"><c:out value="${comment.reportCount}" /></span>
			<a href="${discardReportsUrl}" class="btn btn-primary" type="button">Descartar denuncias</a>
		</p>
	</div>
</c:forEach>

<%@ include file="footer.jsp" %>