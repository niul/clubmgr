<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html itemscope itemtype="http://schema.org/Article" xmlns="http://www.w3.org/1999/xhtml" 
	xmlns:og="http://opengraphprotocol.org/schema/" xmlns:fb="http://www.facebook.com/2008/fbml"  lang="en">
	<head>
		<tiles:insertAttribute name="head"/>
	</head>
	<body class="right-sidebar">
		<div id="page-wrapper">
			<tiles:insertAttribute name="header"/>
			<div id="main-wrapper">
				<div class="container">
					<h1><fmt:message key="admin.title"/></h1>
					<div class="row 200%">
			    		<tiles:insertAttribute name="menu"/>
			    		<tiles:insertAttribute name="content"/>
			    	</div>
				</div>
			</div>	
			<tiles:insertAttribute name="footer"/>
			<tiles:insertAttribute name="javascript"/>
		</div>
	</body>
</html>
