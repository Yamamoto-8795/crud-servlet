<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.example.repositries.PrefectureRepository"%>
<%@ page import="com.example.entitis.PrefectureEntity"%>

<html>
<head>
<title>ユーザー登録</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css" type="text/css" />
<body>

	<div class="form">
		<h1>ユーザー登録</h1>
		<% if (request.getAttribute("errorMessage") != null) { %>
		<div class="error-message">
			<%=request.getAttribute("errorMessage")%>
		</div>
		<% } %>
		<% if (request.getAttribute("nameError") != null) { %>
		<div class="error-message">
			<%=request.getAttribute("nameError")%>
		</div>
		<% } %>

		<% if (request.getAttribute("emailError") != null) { %>
		<div class="error-message">
			<%=request.getAttribute("emailError")%>
		</div>
		<% } %>
		<% if (request.getAttribute("emailError2") != null) { %>
		<div class="error-message">
			<%=request.getAttribute("emailError2")%>
		</div>
		<% } %>
         <% if (request.getAttribute("emailError3") != null) { %>
		<div class="error-message">
			<%=request.getAttribute("emailError3")%>
		</div>
		<% } %>
		<% if (request.getAttribute("prefectureError") != null) { %>
		<div class="error-message">
			<%=request.getAttribute("prefectureError")%>
		</div>
		<% } %>
		<form action="<%=request.getContextPath()%>/users" method="post">
			<div class="form-group">
				<label for="name">姓名</label> <input type="text"
					placeholder="例: 山田　太郎" id="name" name="name" >
			</div>

			<div class="form-group">
				<label for="email">メールアドレス</label> <input type="email"
					placeholder="例: sample@example.com" id="email" name="email">
			</div>

			<div class="form-group">
				<label for="prefectureId">都道府県</label> <select id="prefectureId"
					name="prefectureId" required>
					<% List<PrefectureEntity> prefectures = (List<PrefectureEntity>) request.getAttribute("prefectures"); %>
					<% if (prefectures != null) { %>
					<% for (PrefectureEntity prefectureList : prefectures) { %>
					<option value="<%=prefectureList.getId()%>"><%=prefectureList.getName()%></option>
					<% } %>
					<% } %>
				</select>
			</div>

			<div class="form-group" class="form-btn">
				<input type="submit" value="登録"> <a href="<%=request.getContextPath()%>/users"
					class="cancel-btn">キャンセル</a>
			</div>
		</form>
	</div>
</body>
</head>
</html>