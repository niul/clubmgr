<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<article>
	<header>
		<h1><fmt:message key="admin.title"/></h1>
		<h3><fmt:message key="login.header"/></h3>
	</header>

	<c:if test="${not empty error}">
		<div class="error form_error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg form_error">${msg}</div>
	</c:if>

	<form:form action="${pageContext.request.contextPath}/login"
		commandName="loginData" method="POST">

		<div class="row 50%">
			<div class="6u$ 12u(medium)">
				<fmt:message key="login.username" var="username"/> 
				<form:input type="text" path="username" placeholder="${username}"/>
				<form:errors path="username" class="form_error" />
			</div>
			<div class="6u$ 12u(medium)">
				<fmt:message key="login.password" var="password"/> 
				<form:input type="password" path="password" placeholder="${password}"/>
				<form:errors path="password" cssClass="form_error" />
			</div>
		</div>
		<div class="row 50%">
			<div class="12u">
				<ul class="actions">
					<li>
						<button type="submit" class="button icon fa-envelope-o"><fmt:message key="login.login"/></button>
					</li>
				</ul>
				<a href='<c:url value="/reset.html"/>'><fmt:message key="login.reset"/></a>?
			</div>
		</div>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
</article>
