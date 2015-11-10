<%@ include file="/WEB-INF/views/taglibs.jsp"%>


	  <fmt:formatDate value="${post.createDate}" var="formattedDate" 
                type="date" pattern="MMMM d, yyyy" />
                
	  <article>
	    <header>
          <h2>${post.title}</h2>
		</header>
		<sup>
			<f:message key="news.posted.on"/>: ${formattedDate}
			<f:message key="news.posted.in"/>
		  	<c:forEach var="category" items="${post.categories}">
				<a href="${post.author.url}/category/${category.slug}" target="_blank">${category.name}</a>
		  	</c:forEach>
		</sup>
		${post.content}
	  </article>
	  <a href="post.html?id=${post.previousId}"><f:message key="post.previous"/></a> | <a href="post.html?id=${post.nextId}"><f:message key="post.next"/></a>