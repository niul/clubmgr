<%@ include file="/WEB-INF/views/taglibs.jsp"%>

      <h2><f:message key="news.title"/></h2>
      <c:forEach var="post" items="${posts}">
      
	  <fmt:formatDate value="${post.createDate}" var="formattedDate" 
                type="date" pattern="MMMM d, yyyy" />
	  <article>
	    <header>
          <h3><a href="post.html?id=${post.id}">${post.title}</a></h3>
		</header>
		<sup>
			<f:message key="news.posted.on"/>: ${formattedDate}
			<f:message key="news.posted.in"/>
		  	<c:forEach var="category" items="${post.categories}">
				<a href="${post.author.url}/category/${category.slug}" target="_blank">${category.name}</a>
		  	</c:forEach>
		</sup>
		<p>
		${post.excerpt}
		<p>
		<a href="post.html?id=${post.id}" class="button icon fa-file-text-o"><f:message key="news.posted.more"/></a>
	  </article>
	  
	  </c:forEach>
	  <a href="news.html?start=${previous}"><f:message key="news.previous"/></a> | <a href="news.html?start=${next}"><f:message key="news.next"/></a>