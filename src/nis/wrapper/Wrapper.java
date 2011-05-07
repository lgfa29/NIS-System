package nis.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Wrapper {
	public void run(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
