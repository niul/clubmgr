<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<article>
	<header>
		<h1><fmt:message key="admin.title"/></h1>
		<h3><fmt:message key="password_change.header"/></h3>
	</header>
	<p><fmt:message key="password_reset.p1"/></p>

	<form:form action="${pageContext.request.contextPath}/admin/change.html"
		commandName="passwordChangeData" method="POST">

		<div class="row 50%">
			<div class="6u$ 12u(medium)">
				<fmt:message key="password_change.password.old" var="oldPassword"/> 
				<form:input type="password" path="oldPassword" placeholder="${oldPassword}"/>
				<form:errors path="oldPassword" class="form_error" />
			</div>
			<div class="6u$ 12u(medium)">
				<fmt:message key="password_change.password" var="password"/> 
				<form:input type="password" path="password" placeholder="${password}"/>
				<form:errors path="password" class="form_error" />
			</div>
			<div class="6u$ 12u(medium)">
				<fmt:message key="password_change.password_repeat" var="passwordRepeat"/> 
				<form:input type="password" path="passwordRepeat" placeholder="${passwordRepeat}"/>
				<form:errors path="passwordRepeat" class="form_error" />
			</div>
		</div>
		<div class="row 50%">
			<div class="12u">
				<ul class="actions">
					<li>
						<button type="submit" class="button icon fa-envelope-o"><fmt:message key="form.submit"/></button>
					</li>
				</ul>
			</div>
		</div>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
</article>
