<div class="alert alert-warning">
	<c:choose>
		<c:when test="${directorFilterError == 'unauthorized'}">
			Debe iniciar sesi&oacute;n para buscar las pel&iacute;culas de un determinado director.
		</c:when>
	</c:choose>
</div>