package nis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nis.wrapper.Wrapper;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = -9121411852855335623L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String className = "nis.wrapper." + req.getParameter("wrapper");
		try{
			Class wrapperClass = Class.forName(className);
			Wrapper wrapper = (Wrapper) wrapperClass.newInstance();
			wrapper.run(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
