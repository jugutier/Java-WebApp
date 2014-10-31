<%@ include file="header.jsp" %>

<c:choose>
	<c:when test="${empty film}">
		<%@ include file="error/film-not-found-error.jsp" %>
	</c:when>
	<c:otherwise>
		<c:if test="${not empty error}">
			<%@ include file="error/comment-error.jsp" %>
		</c:if>
		<h2><c:out value="${film.name}"/></h2>
		<%-- <form:form method="post" action="../image/addImage" commandName="image" enctype="multipart/form-data">
		<input type="hidden" name="filmId" class="input-block-level" value="${film.id}">
		  <table>
		    <tr>
		      <td><form:label path="content">Image</form:label></td>
		      <td><input type="file" name="file" id="file"></input></td>
		    </tr>
		    <tr>
		      <td colspan="2">
		        <input type="submit" value="Upload"/>
		      </td>
		    </tr>
		  </table>
		</form:form> --%>
		<%-- <form role="form" action="../image/addImage" method="POST" enctype="multipart/form-data">
			<div class="form-group">
				<input type="hidden" name="filmId" class="input-block-level" value="${film.id}">
			    <label for="exampleInputFile">Seleccione un archivo</label>
			    <input type="file" name="file" id="file">
			    <p class="help-block">Sera usado como imagen de esta pelicula.</p>
			 </div>
			 <button type="submit" class="btn btn-default">Cargar</button>
		</form> --%>
		<div class="row">
			<div class="col-md-4">
			<img data-src="holder.js/140x140" class="img-thumbnail" alt="140x140" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ0LjA0Njg3NSIgeT0iNzAiIHN0eWxlPSJmaWxsOiNBQUFBQUE7Zm9udC13ZWlnaHQ6Ym9sZDtmb250LWZhbWlseTpBcmlhbCwgSGVsdmV0aWNhLCBPcGVuIFNhbnMsIHNhbnMtc2VyaWYsIG1vbm9zcGFjZTtmb250LXNpemU6MTBwdDtkb21pbmFudC1iYXNlbGluZTpjZW50cmFsIj4xNDB4MTQwPC90ZXh0PjwvZz48L3N2Zz4=" data-holder-rendered="true" style="width: 140px; height: 140px;">
				<!-- <img src="..." alt="film-Image" class="img-thumbnail"> -->
			</div>
			<div class="col-md-4 col-md-offset-4">
				<dl class="dl-horizontal">
				
					<dt>Descripcion</dt>
					<dd><c:out value="${film.description}"/></dd>
					<dt>Director</dt>
					<dd><c:out value="${film.director}"/></dd>
					<dt>G&eacute;nero</dt>
					<dd><c:out value="${film.genre}"/></dd>
					<dt>Fecha de estreno</dt>
					<dd><fmt:formatDate value="${film.releaseDate}" pattern="dd-MM-yyyy"/></dd>
					<dt>Duraci&oacute;n</dt>
					<dd><c:out value="${film.length}"/> minutos</dd>
				
				</dl>
			</div>
		  
		</div>
		
		
		<h3>Comentarios</h3>
		
		<%@ include file="commentList.jsp" %>
		
		<c:if test="${empty commentList}">
			<h4 class="muted">No hay comentarios acerca de esta pel&iacute;cula</h4>
		</c:if>
		<hr/>
		<c:choose>
			<c:when test="${empty loggedInUser}">
				<h4 class="muted">Debe iniciar sesi&oacute;n para comentar acerca de esta pel&iacute;cula</h4>
			</c:when>
			<c:when test="${!userCanComment}">
				<h4 class="muted">No puede comentar</h4>
			</c:when>
			<c:otherwise>
				<%@ include file="newComment.jsp" %>
			</c:otherwise>
		</c:choose>

	</c:otherwise>
</c:choose>

<%@ include file="footer.jsp" %>
