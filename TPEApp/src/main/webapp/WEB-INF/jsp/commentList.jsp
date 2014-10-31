<c:forEach items="${commentList}" var="comment">
	<div class="comment-body">
		<c:url value="../film/removeCommentFromFilm" var="removeCommentUrl">
			<c:param name="id" value="${comment.id}" />
			<c:param name="film" value="${film.id}" />
		</c:url>
		<c:if test="${loggedInUser.admin}">
			<a href="${removeCommentUrl}" class="btn btn-danger pull-right" type="button"><i class="icon-remove"></i></a>
		</c:if>
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
		<c:url value="../comment/${comment.id}/report" var="reportUrl">
		</c:url>
		<form class="form-inline" action="filmDetails?id=${film.id}" method="POST" commandName="commentForm">
			Puntuaci&oacute;n: <strong>2.6</strong>
			<c:if test="${(not empty loggedInUser) && (not comment.belongsToUser)}">
				<select class="span1" path="rating">
					<c:forEach begin="0" end="5" var="i">
						<option <c:if test="${i == 3}"> selected </c:if> >
							<c:out value="${i}"/>
						</option>
					</c:forEach>
				</select>
				<button class="btn btn-primary" type="submit">Puntuar</button>
				<c:choose>
					<c:when test="${comment.reportable}">
						<a href="${reportUrl}" class="btn btn-warning pull-right" type="button"><i class="icon-flag"></i> Denunciar</a>
					</c:when>
					<c:otherwise>
						<button href="#" class="btn btn-warning pull-right disabled" type="button"><i class="icon-flag"></i> Denunciar</button>
					</c:otherwise>
				</c:choose>
			</c:if>
		</form>
	</div>
</c:forEach>