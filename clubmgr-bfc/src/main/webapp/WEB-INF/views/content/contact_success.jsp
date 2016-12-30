<%@ include file="/WEB-INF/views/taglibs.jsp"%>

	  <div id="content">
        <div class="content_item">
          <h2><fmt:message key="contact_success.title"/></h2>
		  <p>
		  	<fmt:message key="contact_success.section.main.p1"/> 
		  		<b>${contactData.name}</b>
		  	<fmt:message key="contact_success.section.main.p2"/> 
		  </p>
		</div>
		<div class="button_medium theme_gradient all_round_corner">
		    <a href="index.html"><fmt:message key="home.back"/></a>
		</div>
      </div>