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

@WebServlet("/")
public class Root extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//json to send response
		JSONObject json = new JSONObject();
		json.put("apiRSAVersion", 1.0);
		
		// CORS configuration
		HeadersUtil.setAccessControlHeaders(resp, "GET");
		
		//send response
		resp.setContentType("application/json");
		resp.getOutputStream().println(json.toString());
	}

}
