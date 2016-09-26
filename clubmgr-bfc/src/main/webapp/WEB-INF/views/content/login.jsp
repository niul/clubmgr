<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<article>
	<header>
		<h1><f:message key="admin.title"/></h1>
	</header>

	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg">${msg}</div>
	</c:if>

	<form:form action="${pageContext.request.contextPath}/login"
		commandName="loginData" method="POST">

		<div class="row 50%">
			<div class="6u 12u(small)">
				<form:label path="username"><f:message key="login.username"/>:</form:label>
				<form:input type="text" path="username" />
				<form:errors path="username" class="form_error" />
			</div>
			<div class="6u 12u(small)">
				<form:label path="password"><f:message key="login.password"/>:</form:label>
				<form:input type="password" path="password" />
				<form:errors path="password" cssClass="form_error" />
			</div>
		</div>
		<div class="row 50%">
			<div class="12u">
				<ul class="actions">
					<li>
						<button type="submit" class="button icon fa-envelope-o"><f:message key="login.login"/></button>
					</li>
				</ul>
			</div>
		</div>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
</article>
