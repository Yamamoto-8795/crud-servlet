<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.example.viewmodels.UserDisplayModel"%>
<html>
<head>
<title>ユーザ一覧</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css" type="text/css"/>
</head>
<body>
	<a href="<%=request.getContextPath()%>/users/new" class="register-btn">新規登録</a>
	<%
	List<UserDisplayModel> displayUsers = (List<UserDisplayModel>) request.getAttribute("users");
	if (displayUsers.isEmpty()) {
	%>
	<h1>ユーザが存在しません</h1>
	<%
	} else {
	%>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>姓名</th>
			<th>メールアドレス</th>
			<th>都道府県</th>
			<th>登録日時</th>
			<th>更新日時</th>
		</tr>
		<%
		for (UserDisplayModel users : displayUsers) {
		%>
		<tr>
			<td><%=users.getId()%></td>
			<td><a href="users/<%=users.getId()%>"><%=users.getName()%></a></td>
			<td><%=users.getEmail()%></td>
			<td><%=users.getPrefectureName()%></td>
			<td><%=users.getFormattedCreatedAt()%></td>
			<td><%=users.getFormattedUpdatedAt()%></td>
		</tr>
		<% } %>
	</table>
	<% } %>
</body>
</html>