package com.example.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.example.entities.PrefectureEntity;
import com.example.entities.UserEntity;
import com.example.repositries.PrefectureRepository;
import com.example.repositries.UserRepository;
import com.example.service.UserService;
import com.example.utilities.Validate;
import com.example.viewmodels.UserDisplayModel;
import com.google.gson.Gson;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserDetailController extends HttpServlet {
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
		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.equals("/")) {
			response.sendRedirect("/error");
			return;
		}

		String userIdParam = pathInfo.substring(1);
		int userId;
		userId = Integer.parseInt(userIdParam);

		try {
			UserDisplayModel userDetails = userService.getUserDetails(userId);
			if (userDetails == null) {
				response.sendRedirect("/error");
				return;
			}
			request.setAttribute("userDetails", userDetails);
			List<PrefectureEntity> prefectures = userService.getAllPrefectures();
			request.setAttribute("prefectures", prefectures);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/userDetail.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/error");
			dispatcher.forward(request, response);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		Gson gson = new Gson();
		UserEntity userUpdate = gson.fromJson(sb.toString(), UserEntity.class);
		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			String[] pathParts = pathInfo.split("/");
			if (pathParts.length > 2) {
				int userId = Integer.parseInt(pathParts[3]);
				userUpdate.setId(userId);
			}
		}
		Map<String, String> errors = Validate.validateUserInput(
				userUpdate.getName(),
				userUpdate.getEmail(),
				String.valueOf(userUpdate.getPrefectureId()),
				userService);

		if (!errors.isEmpty()) {
			response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(gson.toJson(errors));
	        return;
		}

		try {
			boolean success = userService.updateUser(userUpdate);
			if (success) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("{\"success\": true}");
			} else {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("{\"error\": \"ユーザー情報の更新に失敗しました。\"}");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error");
			dispatcher.forward(request, response);
		}
	}

}
