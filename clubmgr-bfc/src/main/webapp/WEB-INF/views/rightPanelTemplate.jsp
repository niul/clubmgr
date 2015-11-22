<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<c:if test="${empty title}">
		<title><f:message key="header.title"/></title>
		</c:if>
		<c:if test="${not empty title}">
		<title><f:message key="header.title"/> - ${title}</title>
		</c:if>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="<f:message key="header.metadata"/>" />
		<meta name="keywords" content="<f:message key="header.title"/>" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src='<c:url value="/static/js/ie/html5shiv.js"/>'/><![endif]-->
		<link rel="stylesheet" href='<c:url value="/static/css/main.css"/>' />
		<!--[if lte IE 8]><link rel="stylesheet" href='<c:url value="/static/css/ie8.css"/>'/><![endif]-->
		<link rel="shortcut icon" href='<c:url value="/static/images/bombasticfc_logo.ico"/>' />
	</head>
	<body class="right-sidebar">
		<div id="page-wrapper">
			<tiles:insertAttribute name="header"/>
			<div id="main-wrapper">
				<div class="container">
					<div class="row 200%">
						<div class="8u 12u$(medium)">
							<div id="content">
			    				<tiles:insertAttribute name="content"/>
			    			</div>
			    		</div>
			    		<div class="4u 12u$(medium)">
							<div id="sidebar">
			    				<tiles:insertAttribute name="rightPanel"/>
			    			</div>
			    		</div>
			    	</div>
				</div>
			</div>	
			<tiles:insertAttribute name="footer"/>
			<script src='<c:url value="/static/js/jquery.min.js"/>'></script>
			<script src='<c:url value="/static/js/jquery.dropotron.min.js"/>'></script>
			<script src='<c:url value="/static/js/skel.min.js"/>'></script>
			<script src='<c:url value="/static/js/skel-layout.min.js"/>'></script>
			<script src='<c:url value="/static/js/util.js"/>'></script>
			<!--[if lte IE 8]><script src='<c:url value="/static/js/ie/respond.min.js"/>'></script><![endif]-->
			<script src='<c:url value="/static/js/main.js"/>'></script>
			<script src='<c:url value="/static/js/social.js"/>'></script>
			<script src='<c:url value="/static/js/maps.js"/>'></script>
		</div>
	</body>
</html>
