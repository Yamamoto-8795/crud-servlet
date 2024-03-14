package com.example.utilities;

import java.util.HashMap;
import java.util.Map;

import com.example.service.UserService;

public class Validate {
	public static Map<String, String> validateUserInput(String name, String email, String prefectureIdStr,UserService userService) {
		
		Map<String, String> errors = new HashMap<>();

		if (name == null || name.trim().isEmpty()) {
			errors.put("nameError", "ユーザー名は必須です 空文字も不可です");
		} else if (name.length() > 30) {
			errors.put("nameError", "ユーザー名は30文字以下で入力して下さい");
		}

		if (email == null || email.trim().isEmpty()) {
			errors.put("emailError", "メールアドレスは必須です");
		} else if (!email.contains("@") || email.length() > 100) {
			errors.put("emailError", "メールアドレスは＠を含み、100文字以下で入力して下さい");
		}

		if (prefectureIdStr == null || prefectureIdStr.isEmpty()) {
			errors.put("prefectureError", "都道府県は必須です");
		}

		try {
			if (userService.emailExist(email)) {
				errors.put("emailError", "このメールアドレスは既に使用されています");
			}
		} catch (Exception e) {
			errors.put("errorMessage", "エラーが発生しました");
		}

		return errors;
	}
}
