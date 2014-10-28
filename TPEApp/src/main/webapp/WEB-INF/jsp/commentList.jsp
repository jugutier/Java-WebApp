<c:forEach items="${commentList}" var="comment">
	<div class="comment-body">
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
		<form class="form-inline" action="filmDetails?id=${film.id}" method="POST" commandName="commentForm">
			<label for="rating">Puntuaci&oacute;n</label>
			<select class="span1" path="rating">
				<c:forEach begin="0" end="5" var="i">
					<option <c:if test="${i == 3}"> selected </c:if> >
						<c:out value="${i}"/>
					</option>
				</c:forEach>
			</select>
			<button class="btn btn-primary" type="submit">Puntuar</button>
			<a class="btn btn-danger" type="button"><i class="icon-flag"></i>Reportar</a>
		</form>
	</div>
</c:forEach>