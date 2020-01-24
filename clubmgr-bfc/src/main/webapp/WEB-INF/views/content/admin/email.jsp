<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<link type='text/css' rel='stylesheet' href='<c:url value="/static/css/jquery.te.css"/>' charset='utf-8' >
<script type='text/javascript' src='<c:url value="/static/js/jquery.te.min.js"/>' charset='utf-8'></script>

	<div id="previewPanel" class="previewPanel" style="display:none">
    	<p>Test</p>
	</div>

	<!-- Main bar -->
	<div class="9u 12u(small)">				
		<!-- Panel -->
		<div class="panel">
			<div class="panel-head">
				<h4><fmt:message key="email.title"/></h4>
			</div>
			<div class="box-content">
				<form:form modelAttribute="email" method="POST">
					<div class="row 50%">
			<div class="8u$ 12u(medium)">
				<fmt:message key="email.subject" var="subject"/> 
				<form:input type="text" path="subject" placeholder="${subject}"/>
				<form:errors path="subject" class="form_error" />
			</div>
			<div class="12u">
				<fmt:message key="email.message" var="message"/> 
				<form:input class="editor" type="textarea" path="message" placeholder="${message}"/>
				<form:errors path="message" class="form_error" />
			</div>
		</div>
		<div class="row 50%">
			<div class="12u">
				<ul class="actions">
					<li>
						<button type="button" class="button icon fa-eye" id="preview"><fmt:message key="form.preview"/></button>
					</li>
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

	<script>
    $(document).ready(function() {
		$(".editor").jqte();
	 });
    
    $(document).on("click", function(e){
        if($(e.target).is("#preview")){
          $("#previewPanel").show();
        }else{
            $("#previewPanel").hide();
        }
    });
	</script>