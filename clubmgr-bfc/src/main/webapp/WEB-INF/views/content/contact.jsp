<%@ include file="/WEB-INF/views/taglibs.jsp"%>

      <article>
        <header>
            <h2><f:message key="contact.title" /></h2>
        </header>
            <p><f:message key="contact.description" /></p>
			
			<form:form action="${pageContext.request.contextPath}/contact.html" 
				commandName="contactData" 
				method="post">
			<div class="row 50%">
			  <div class="6u 12u(mobilep)">
				<form:label path="name"><f:message key="contact.name" />:</form:label>
				<form:input type="text" path="name"/>
				<form:errors path="name" class="form_error" />
			  </div>
			  <div class="6u 12u(mobilep)">
				<form:label path="email"><f:message key="contact.email" />:</form:label>
				<form:input type="text" path="email"/>
				<form:errors path="email" cssClass="form_error"/>
			  </div>
			</div>
			<div class="row 50%">
			  <div class="12u">
				<form:label path="message"><f:message key="contact.message" />:</form:label>
				<form:textarea rows="8" cols="50" class="contact" path="message" />
			  </div>
			</div>
			<div class="row 50%">
			  <div class="12u">
			    <ul class="actions">
			      <li><input type="submit" class="button alt" value="<f:message key='contact.submit' />" /></li>
				</ul>
			  </div>
			</div>
          </form:form>
        </article>