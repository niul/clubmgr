<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	<!-- Main bar -->
	<div class="9u 12u(small)">				
		<!-- Panel -->
		<div class="panel">
			<div class="panel-head">
				<h4><fmt:message key="player.add.title"/> - ${team.name}</h4>
			</div>
			<div class="box-content">
				<form:form modelAttribute="player" method="POST">
					<div class="row 50%">
			<div class="8u$ 12u(medium)">
				<fmt:message key="player.add.first_name" var="firstName"/> 
				<form:input type="text" path="firstName" placeholder="${firstName}"/>
				<form:errors path="firstName" class="form_error" />
			</div>
			<div class="8u$ 12u(medium)">
				<fmt:message key="player.add.last_name" var="lastName"/> 
				<form:input type="text" path="lastName" placeholder="${lastName}"/>
				<form:errors path="lastName" class="form_error" />
			</div>
			<div class="8u$ 12u(medium)">
				<fmt:message key="player.add.email" var="email"/> 
				<form:input type="text" path="email" placeholder="${email}"/>
				<form:errors path="email" class="form_error" />
			</div>
			<div class="8u$ 12u(medium)">
				<fmt:message key="player.add.phone" var="phone"/> 
				<form:input type="text" path="phone" placeholder="${phone}"/>
				<form:errors path="phone" class="form_error" />
			</div>
			<div class="8u$ 12u(medium)">
				<form:label path="position"><fmt:message key="player.add.position"/>:</form:label> 
				<form:select path="position" class="single">
					<form:options items="${positions}" itemValue="key" itemLabel="description"/>
				</form:select>
				<form:errors path="position" class="form_error" />
			</div>
			<c:if test="${fn:length(teams) gt 1}">
			<div class="8u$ 12u(medium)">
				<form:label path="teams"><fmt:message key="player.add.teams"/>:</form:label> 
				<form:select multiple="true" size="${fn:length(teams)}" path="teams">
					<form:options items="${teams}" itemValue="uuid" itemLabel="name"/>
				</form:select>
				<form:errors path="teams" class="form_error" />
			</div>
			</c:if>
		</div>
		<div class="row 50%">
			<div class="12u">
				<ul class="actions">
					<li>
						<button type="submit" class="button icon fa-plus"><fmt:message key="form.add"/></button>
					</li>
					<li>
						<button type="reset" class="button icon fa-undo"><fmt:message key="form.reset"/></button>
					</li>
				</ul>
			</div>
		</div>
				</form:form>
			</div>
		</div>
	</div>
