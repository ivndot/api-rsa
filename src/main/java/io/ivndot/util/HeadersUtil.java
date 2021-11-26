package io.ivndot.util;

import javax.servlet.http.HttpServletResponse;

public class HeadersUtil {
	
	/**
	 * CORS configuration to enable all origins
	 * 
	 * @param resp Servlet response
	 * @param methods Allowing methods
	 * 
	 */
	public static void setAccessControlHeaders(HttpServletResponse resp, String methods) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", methods);
	}
	

}
