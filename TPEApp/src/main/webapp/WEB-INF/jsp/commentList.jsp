<c:forEach items="${commentList}" var="comment">
	<div class="comment-body">
		<c:if test="${loggedInUser.admin}">
			<form class="form-inline pull-right" action="${pageContext.request.contextPath}/bin/comment/removeComment" method="POST">
				<input type="hidden" name="fromPage" value="${fromPage}" />
				<input type="hidden" name="film" value="${film.id}" />
				<input type="hidden" name="id" value="${comment.id}" />
				<button href="#" class="btn btn-danger" type="submit"><i class="icon-remove"></i></button>
			</form>
		</c:if>
		<p>
			<c:url value="profile" var="detailUrl">
				<c:param name="id" value="${comment.user.id}" />
			</c:url>			
			<a href="${pageContext.request.contextPath}/bin/user/${detailUrl}"><strong><c:out value="${comment.user.name}"/></strong></a>
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
		<c:url value="/bin/comment/${comment.id}/report" var="reportUrl">
		</c:url>
		<form class="form-inline" action="${pageContext.request.contextPath}/bin/comment/${comment.id}/rate" method="POST" commandName="commentForm">
			Puntuaci&oacute;n: <strong>
			<c:choose>
				<c:when test="${comment.rated}">
					<fmt:formatNumber value="${comment.rate}" maxFractionDigits="2" />
				</c:when>
				<c:otherwise>
					--
				</c:otherwise>
			</c:choose>
			</strong>
			<c:if test="${(not empty loggedInUser) && (not comment.belongsToUser)}">
					<c:if test="${(not comment.ratedByUser)}">
						<select class="span1" name="rating">
							<c:forEach begin="0" end="5" var="i">
								<option <c:if test="${i == 0}"> selected </c:if> >
									<c:out value="${i}"/>
								</option>
							</c:forEach>
						</select>
						<button class="btn btn-primary" type="submit">Puntuar</button>
					</c:if>
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