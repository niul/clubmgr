<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<article>
	<header>
		<h1><fmt:message key="admin.title"/></h1>
	</header>
	<p><fmt:message key="password_email.p1"/></p>

	<form:form action="${pageContext.request.contextPath}/reset.html"
		modelAttribute="passwordEmailData" method="POST">

		<div class="row 50%">
			<div class="6u$ 12u(medium)">
				<fmt:message key="password_email.email" var="email"/> 
				<form:input type="email" path="email" placeholder="${email}"/>
				<form:errors path="email" class="form_error" />
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
