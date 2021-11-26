package io.ivndot.routes;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import io.ivndot.util.HeadersUtil;

@WebServlet("/login")
public class Login extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get parameters
		String user = req.getParameter("user");
		String passwd = req.getParameter("password");

		// json object to send result
		JSONObject json = new JSONObject();

		if (user != null && !user.isEmpty() && passwd != null && !passwd.isEmpty()) {
			// user and passwd have content
			if (user.equals("Virologia") && passwd.equals("4NV70")) {
				// user and password are correct
				generateJSON(json, "ok", "The user and password are correct");
			} else {
				// the user or password is wrong
				generateJSON(json, "error", "The user or password is wrong");
			}
		} else {
			// the parameters are empty
			generateJSON(json, "error", "The parameters are empty");
		}
		
		// CORS configuration
		HeadersUtil.setAccessControlHeaders(resp, "POST");
		//send response
		resp.setContentType("application/json");
		resp.getOutputStream().println(json.toString());
	}

	/**
	 * Generate JSON object to response the request
	 * 
	 * @param json        JSONObject
	 * @param status      "ok" wether there is no error or "error" if there is
	 * @param description Short description of the status
	 */
	private void generateJSON(JSONObject json, String status, String description) {
		json.put("status", status);
		json.put("description", description);
	}

}
