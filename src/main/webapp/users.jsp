<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.example.viewmodels.UserDisplayModel"%>
<html>
<head>
<title>ユーザ一覧</title>
<link rel="stylesheet" href="styles/style.css" type="text/css" />
<script>
function deleteUser(userId) {
  if (confirm("本当に削除しますか？")) {
    let url = '/test2/users/' + userId;
    fetch(url, {
      method: "DELETE"
    }).then(response => {
        if (!response.ok) {
            return response.json().then(data => {
              throw new Error(data.error);
            });
        } 
        return response.json();
        })
      .then(data => {
        if (data.success) {
            alert("削除しました");
            window.location.reload();
        } else if(data.error) {
            alert(data.error);
        } else {
            alert("削除に失敗しました")
        }
    }).catch (error => {
        alert("Error" + error.message);
    });
  } 
}
</script>
</head>
<body>
  <a href="<%=request.getContextPath()%>/users/new" class="register-btn">新規登録</a>
    <% List<UserDisplayModel> displayUsers = (List<UserDisplayModel>) request.getAttribute("users");
      if (displayUsers.isEmpty()) { %>
        <h1>ユーザが存在しません</h1>
    <% } else { %>
      <table border="1">
        <tr>
          <th>ID</th>
          <th>姓名</th>
          <th>メールアドレス</th>
          <th>都道府県</th>
          <th>登録日時</th>
          <th>更新日時</th>
        </tr>

      <% for (UserDisplayModel users : displayUsers) { %>
          <tr>
            <td><%=users.getId()%></td>
            <td><a href="users/<%=users.getId()%>"><%=users.getName()%></a></td>
            <td><%=users.getEmail()%></td>
            <td><%=users.getPrefectureName()%></td>
            <td><%=users.getFormattedCreatedAt()%></td>
            <td><%=users.getFormattedUpdatedAt()%></td>
            <td><button class="button-delete" onclick="deleteUser(<%=users.getId()%>)">削除</button></td>
          </tr>
      <% } %>
      </table>
    <% } %>
</body>
</html>