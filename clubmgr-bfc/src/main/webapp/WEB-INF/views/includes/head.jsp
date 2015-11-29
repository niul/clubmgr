<%@ include file="/WEB-INF/views/taglibs.jsp" %>

      	<f:message key="header.base_url" var="baseUrl"/>
      	<c:set var="params" value="${pageContext.request.queryString}"/>
      	<c:if test="${empty params}">
			<c:set var="url" value="${baseUrl}${requestScope['javax.servlet.forward.request_uri']}" scope="request"/>
		</c:if>
      	<c:if test="${not empty params}">
      		<c:set var="url" value="${baseUrl}${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}" scope="request"/>
      	</c:if>
 
		<c:if test="${empty title}">
		<title><f:message key="header.title"/></title>
		<meta property="og:title" content="<f:message key="header.title"/>" />
		<meta name="twitter:title" content="<f:message key="header.title"/>" />
		<meta itemprop="name" content="<f:message key="header.title"/>">
		</c:if>
		<c:if test="${not empty title}">
		<title><f:message key="header.title"/> - ${title}</title>
		<meta property="og:title" content="<f:message key="header.title"/> - ${title}" />
		<meta name="twitter:title" content="<f:message key="header.title"/> - ${title}" />
		<meta itemprop="name" content="<f:message key="header.title"/> - ${title}">
		</c:if>
		
		<meta property="og:type" content="website" />
		<meta property="og:description" content="<f:message key="header.metadata.description"/>" />
		<meta property="og:url" content="${url}" />
		<meta property="og:site_name" content="<f:message key="header.metadata.name"/>" />
		<meta property="og:image" content="https://secure.gravatar.com/blavatar/8a99c879a84cd362fb7c96f3d322ae1d?s=200&amp;ts=1448659724" />
		<meta property="og:image:width" content="200" />
		<meta property="og:image:height" content="200" />
		<meta property="og:locale" content="en_US" />
		
		<meta name="twitter:site" content="@bombasticfc" />
		<meta name="twitter:description" content="<f:message key="header.metadata.description"/>" />
		<meta name="twitter:image" content="https://secure.gravatar.com/blavatar/8a99c879a84cd362fb7c96f3d322ae1d?s=240" />
		<meta name="twitter:card" content="summary" />
		<meta name="twitter:creator" content="@bombasticfc" />
		<meta name="twitter:url" content="${url}"/>
		
		<meta property="fb:admins" content="BombasticFC" />
		
		<!-- Schema.org markup for Google+ -->
		<meta itemprop="description" content="<f:message key="header.metadata.description"/>">
		<meta itemprop="image" content="https://secure.gravatar.com/blavatar/8a99c879a84cd362fb7c96f3d322ae1d?s=200&amp;ts=1448659724">
		
		<meta name="description" content="<f:message key="header.metadata.description"/>" />
		<meta name="keywords" content="<f:message key="header.metadata.keywords"/>" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		
		<!--[if lte IE 8]><script src='<c:url value="/static/js/ie/html5shiv.js"/>'/><![endif]-->
		<link rel="stylesheet" href='<c:url value="/static/css/main.min.css"/>' />
		<!--[if lte IE 8]><link rel="stylesheet" href='<c:url value="/static/css/ie8.css"/>'/><![endif]-->
		<link rel="shortcut icon" href='<c:url value="/static/images/bombasticfc_logo.ico"/>' />