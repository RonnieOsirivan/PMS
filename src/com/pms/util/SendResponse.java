package com.pms.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendResponse {
	
	private static SendResponse sendRes = null;
	
	private SendResponse(){
	}
	
	public static SendResponse getInstance(){
		if(sendRes == null){
			sendRes = new SendResponse();
		}
		return sendRes;
	}
	
	public void sendRessponseToView(HttpServletRequest request,HttpServletResponse response,String resultJson){
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(resultJson);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
