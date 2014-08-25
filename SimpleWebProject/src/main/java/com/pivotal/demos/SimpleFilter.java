package com.pivotal.demos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName="SimpleFilter", urlPatterns="/filter-test", asyncSupported=true)
public class SimpleFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Content-Type", "text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<title>Index</title>");
		out.println("<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<!--[if lt IE 9]>");
		out.println("<script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>");
		out.println("<script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>");
		out.println("<![endif]-->");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hello from a Filter</h1>");
		out.println("<p>This is a sample web page.  It does nothing.  It simply serves as something for our load tests to download.</p>");
		out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script>");
		out.println("<script src=\"js/bootstrap.min.js\"></script>");
		out.println("</body>");
		out.println("</html>");
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

}
