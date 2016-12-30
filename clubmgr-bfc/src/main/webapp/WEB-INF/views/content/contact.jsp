<%@ include file="/WEB-INF/views/taglibs.jsp"%>

      <article>
        <header>
            <h1><fmt:message key="contact.title" /></h1>
        </header>
            <p><fmt:message key="contact.description" /></p>
			
			<form:form action="${pageContext.request.contextPath}/contact.html" 
				commandName="contactData" 
				method="post">
			<div class="row 50%">
			  <div class="6u 12u(small)">
				<form:label path="name"><fmt:message key="contact.name" />:</form:label>
				<form:input type="text" path="name"/>
				<form:errors path="name" class="form_error" />
			  </div>
			  <div class="6u 12u(small)">
				<form:label path="email"><fmt:message key="contact.email" />:</form:label>
				<form:input type="text" path="email"/>
				<form:errors path="email" cssClass="form_error"/>
			  </div>
			</div>
			<div class="row 50%">
			  <div class="12u">
				<form:label path="message"><fmt:message key="contact.message" />:</form:label>
				<form:textarea rows="8" cols="50" class="contact" path="message" />
			  </div>
			</div>
			<div class="row 50%">
			  <div class="12u">
			    <ul class="actions">
			      <li>
			      	<button type="submit" class="button icon fa-envelope-o"><fmt:message key='form.submit' /></button>
			      </li>
				</ul>
			  </div>
			</div>
          </form:form>
        </article>