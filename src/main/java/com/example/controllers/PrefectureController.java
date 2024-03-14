package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.entities.PrefectureEntity;
import com.example.repositries.PrefectureRepository;
import com.example.repositries.UserRepository;
import com.example.service.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PrefectureController extends HttpServlet {
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
		PrefectureRepository repository = new PrefectureRepository();
		List<PrefectureEntity> prefectures;

		try {
			prefectures = repository.findAll();

			request.setAttribute("prefectures", prefectures);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error");
			dispatcher.forward(request, response);
		}
	}

}