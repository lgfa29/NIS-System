package nis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveQueryServlet extends HttpServlet {
	private static final long serialVersionUID = -9218547040461271790L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String query = (String)request.getParameter("query");
		request.getSession().setAttribute("query", query);
	}
}
