package net.phyokyaw.jaquapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AquaWebControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType( "text/html" );
		out.println("<HTML>");
		out.println("<HEAD><TITLE>MyServlet (no args)</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<H1>MyServlet</H1>");
		out.println("<BODY>MyServlet</BODY>");
		out.flush();
	}
}
