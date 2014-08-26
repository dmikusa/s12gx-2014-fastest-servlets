package com.pivotal.demos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@WebServlet(name="ThymeleafServlet", urlPatterns="/thymeleaf-test", asyncSupported=true)
public class ThymeleafServlet extends HttpServlet {
	private static final long serialVersionUID = -2648741649091514500L;

	private TemplateEngine templateEngine;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		templateEngine.process("thymeleaf-test",
							   new WebContext(req, resp, getServletContext()),
							   resp.getWriter());
	}

	@Override
	public void init() throws ServletException {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCacheable(false);

		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}
}
