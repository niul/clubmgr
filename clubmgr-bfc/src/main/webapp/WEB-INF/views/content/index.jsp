<%@ include file="/WEB-INF/views/taglibs.jsp"%>

<fmt:formatDate value="${post[0].createDate}" var="formattedDate" 
                type="date" pattern="MMMM d, yyyy" />

<!-- Gigantic Heading -->
<section class="wrapper style2">
  <div class="container">
    <header class="major">
	  <h2><f:message key="index.title"/></h2>
	  <p><f:message key="index.section.main"/></p>
	</header>
	<article>
	    <header>
	      <h2><f:message key="index.section.news"/></h2>
          <h3>${posts[0].title}</h3>
		</header>
		<sup>
			<f:message key="news.posted.on"/>: ${formattedDate}
			<f:message key="news.posted.in"/>
		  	<c:forEach var="category" items="${posts[0].categories}">
				<a href="${posts[0].author.url}/category/${category.slug}" target="_blank">${category.name}</a>
		  	</c:forEach>
		</sup>
		${posts[0].content}
	  </article>
  </div>
</section>
