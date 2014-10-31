<%@ include file="header.jsp" %>

<h2>Comentarios denunciados</h2>

<c:forEach items="${commentList}" var="comment">
	<div class="comment-body">
		<c:url value="../film/removeCommentFromFilm" var="removeCommentUrl">
			<c:param name="id" value="${comment.id}" />
		</c:url>
		<c:url value="../user/discardReports" var="discardReportsUrl">
			<c:param name="id" value="${comment.id}" />
		</c:url>
		<a href="${removeCommentUrl}" class="btn btn-danger pull-right" type="button"><i class="icon-remove"></i></a>
		<p>
			<c:if test="${comment.user.vip}">
				<i class="icon-ok"></i>
			</c:if>
			<strong><c:out value="${comment.user.name}"/></strong>
		</p>
		<p>
			<c:forEach begin="1" end="${comment.rate}" var="i">  
				<i class="icon-star"></i>
			</c:forEach>
			<c:forEach begin="${comment.rate + 1}" end="5" var="i">  
				<i class="icon-star-empty"></i>
			</c:forEach>
		</p>
		<p>
			<c:out value="${comment.text}"/>
		</p>
	</div>
	<div class="comment-controls">
		<p>
			Denuncias: <strong><c:out value="${comment.reportCount}" /></strong>
			<a href="${discardReportsUrl}" class="btn btn-primary pull-right" type="button">Descartar denuncias</a>
		</p>
	</div>
</c:forEach>

<%@ include file="footer.jsp" %>