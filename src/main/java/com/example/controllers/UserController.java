package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.entities.PrefectureEntity;
import com.example.entities.UserEntity;
import com.example.repositries.PrefectureRepository;
import com.example.repositries.UserRepository;
import com.example.service.UserService;
import com.example.utilities.Validate;
import com.example.viewmodels.UserDisplayModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {
	private transient UserService userService;

	@Override
	public void init() {
		UserRepository userRepository = new UserRepository();
		PrefectureRepository prefectureRepository = new PrefectureRepository();
		this.userService = new UserService(userRepository, prefectureRepository);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		List<UserEntity> users = null;
		List<PrefectureEntity> prefectures = null;
		List<UserDisplayModel> displayUsers = new ArrayList<>();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			users = userService.getAllUsers();
			prefectures = userService.getAllPrefectures();

			for (UserEntity user : users) {
				String prefectureName = userService.getPrefectureNameById(user.getPrefectureId(), prefectures);
				String formattedCreatedAt = dateFormat.format(user.getCreatedAt());
				String formattedUpdatedAt = dateFormat.format(user.getUpdatedAt());
				displayUsers.add(new UserDisplayModel(user.getId(), user.getName(), user.getEmail(), prefectureName, formattedCreatedAt, formattedUpdatedAt));
		    }
			request.setAttribute("users", displayUsers);
			RequestDispatcher dispatcher = request.getRequestDispatcher("users.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error");
			dispatcher.forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String prefectureIdStr = request.getParameter("prefectureId");

		 Map<String, String> errors = Validate.validateUserInput(name, email, prefectureIdStr, userService);

		    if (!errors.isEmpty()) {
		        for (Map.Entry<String, String> error : errors.entrySet()) {
		            request.setAttribute(error.getKey(), error.getValue());
		        }
		        forwardToRegister(request, response);
		        return;
		    }
		int prefectureId = Integer.parseInt(prefectureIdStr);
		UserEntity newUser = new UserEntity();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPrefectureId(prefectureId);

		int success = userService.createUser(newUser);

		if (success > 0) {
			response.sendRedirect("users");
		} else {
			request.setAttribute("errorMessage", "ユーザー登録に失敗しました");
			forwardToRegister(request, response);
			return;
		}

	}

	private void forwardToRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<PrefectureEntity> prefectures = userService.getAllPrefectures();
			request.setAttribute("prefectures", prefectures);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "都道府県のリストの取得に失敗しました");
		}
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}
}
