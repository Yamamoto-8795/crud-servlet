<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Error</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css" type="text/css" />
</head>
<body>
	<div class="error-message">
		<h1>Error</h1>
		<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
		<% if(errorMessage != null) { %>
		<p class="details"><%= errorMessage %></p>
		<% } %>
	</div>
	<div class=back>
		<a href="<%=request.getContextPath()%>/users/new">戻る</a>
	</div>
</body>
</html>
