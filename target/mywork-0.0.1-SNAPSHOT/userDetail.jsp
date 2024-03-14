<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List"%>
<%@ page import="com.example.viewmodels.UserDisplayModel"%>
<%@ page import="com.example.entitis.PrefectureEntity" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー詳細</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/userDetailsStyle.css">

　　<script>
        document.addEventListener("DOMContentLoaded", function(){
        var editButton = document.getElementById("editButton");
        var cancelButton = document.getElementById("cancelButton");
        var updateForm = document.getElementById("updateForm");
        var userDetails = document.getElementById("userDetails");
        var userId = userDetails.getAttribute("data-user-id");
        
        editButton.addEventListener('click', toggleForm);
        updateForm.addEventListener("submit",function(event){
        updateUserData(event, userId); 
        });
        
    });
    
	function toggleForm() {
		var form = document.getElementById("updateForm");
		var details = document.getElementById("userDetails");
		if (form.style.display === "none") {
			form.style.display = "block";
			details.style.display = "none";
		} else {

			form.style.display = "none";
			details.style.display = "block";
		}
	}
	function confirmUpdate() {
		return confirm("入力内容に誤りはありませんか？");
	}

	function confirmCancel() {
		if (confirm("変更内容をキャンセルしてもよろしいですか？")) {
			toggleForm();
		}
	}
 

	function updateUserData(event, userId) {
		event.preventDefault();
		if (!confirmUpdate()) return;
		
		var form =document.getElementById("updateFormData");
		var formData = new FormData(form);
		const object = {};
		formData.forEach(function(value, key){
            object[key] = value;
			});
		var url = 'mywork/users/' + userId;
		fetch(url, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json", 
                },
            body: JSON.stringify(object)
			})
		.then(response => response.json())
		.then(data => {
			console.log(data);
			if (data.success){
			 window.location.href = '/mywork/users/' + userId;
			} else {
                displayErrors(data);
				}		
			})
		.catch (error => {
			alert(`Error: ${error.message}`);
			});
		}
	
	function displayErrors(errors) {
		const errorContainer = document.getElementById('errorContainer');
	    errorContainer.innerHTML = ''; 

	    for (const [key, value] of Object.entries(errors)) {
	        const errorDiv = document.createElement('div');
	        errorDiv.textContent = value;
	        errorDiv.classList.add('error-message'); 
	        errorContainer.appendChild(errorDiv);
	    }
	}
</script>
</head>
     <%UserDisplayModel user = (UserDisplayModel) request.getAttribute("userDetails");%>
<body>
    <div id="errorContainer"></div>
	<div id="userDetails" class="container" data-user-id="<%=user.getId()%>">
		<h2>ユーザー詳細</h2>
		<p>姓名:<%=user.getName()%></p>
		<p>メールアドレス:<%=user.getEmail()%></p>
		<p>都道府県:<%=user.getPrefectureName()%></p>
		<p>登録日時:<%=user.getFormattedCreatedAt()%></p>
		<p>更新日時:<%=user.getFormattedUpdatedAt()%></p>
		<div class="button-group">
			<button id="editButton" class="btn-common">編集</button>
			<a href="<%=request.getContextPath()%>/users" class="btn-common">戻る</a>
		</div>
	</div>

	<div id="updateForm" class="container" style="display:none;">
		<h2>ユーザー情報更新</h2>
		<form action="javaScript:void(0)" id="updateFormData"　>
			姓名: <input type="text" name="name" value="<%=user.getName()%>"><br>
			メールアドレス: <input type="email" name="email"
				value="<%=user.getEmail()%>"><br> 都道府県: <select
				id="prefectureId" name="prefectureId" required>
				<%List<PrefectureEntity> prefectures = (List<PrefectureEntity>) request.getAttribute("prefectures");%>
				<%if (prefectures != null) {%>
				<%for (PrefectureEntity prefecture : prefectures) {%>
				<%if (user.getPrefectureId() == prefecture.getId()) { %>
				<option value="<%=prefecture.getId()%>" selected="selected"><%=prefecture.getName()%></option>
				<% } else { %>
				<option value="<%=prefecture.getId()%>"><%=prefecture.getName()%></option>
				<%}%><br>
				<%}%>
				<%}%>
			</select>
				<input type="submit" value="更新" class="btn-common">
		</form>
		<button onclick="confirmCancel()" class="btn-common">キャンセル</button>
	</div>
</body>
</html>