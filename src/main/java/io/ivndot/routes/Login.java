package io.ivndot.routes;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.ivndot.beans.LoginBean;
import io.ivndot.util.ResponseUtil;

@WebServlet("/login")
public class Login extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get parameters
		String user = req.getParameter("user");
		String passwd = req.getParameter("password");

		// login bean
		LoginBean loginBean = null;

		if (user != null && !user.isEmpty() && passwd != null && !passwd.isEmpty()) {
			// user and passwd have content
			if (user.equals("Virologia") && passwd.equals("4NV70")) {
				// user and password are correct
				loginBean = new LoginBean(100, "ok", "The user and password are correct");
			} else {
				// ERROR: the user or password is wrong
				loginBean = new LoginBean(200, "error", "The user or password is wrong");
			}
		} else {
			// ERROR: the parameters are empty
			loginBean = new LoginBean(201, "error", "The parameters are empty");
		}

		// send response
		ResponseUtil.sendJSONResponse(resp, loginBean, "POST");
	}

}
