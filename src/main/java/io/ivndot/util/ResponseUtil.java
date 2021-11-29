package io.ivndot.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class ResponseUtil {

	/**
	 * CORS configuration to enable all origins
	 * 
	 * @param resp    Servlet response
	 * @param methods Allowing methods
	 * 
	 */
	public static void setAccessControlHeaders(HttpServletResponse resp, String methods) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", methods);
	}

	/**
	 * Function to send the response in JSON to the client
	 * 
	 * @param resp HttpServletResponse
	 * @param bean Object (bean) with properties that will be parse into JSON
	 */
	public static void sendJSONResponse(HttpServletResponse resp, Object bean) {

		try {
			resp.setContentType("application/json");
			// CORS configuration
			ResponseUtil.setAccessControlHeaders(resp, "POST");
			// send response
			resp.getOutputStream().println(new JSONObject(bean).toString());
		} catch (IOException e) {
			// ERROR: there was an error sending the response
			System.out.println("Error sending the response: ");
			e.printStackTrace();
		}

	}

}
