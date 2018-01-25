package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.EmaillistDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.EmaillistVo;

@WebServlet("/el") // http://localhost:8088/emillist2/el?a(ction)=form
public class EmaillistServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("servlet 진입");

		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		if ("form".equals(actionName)) {
			System.out.println("form 진입");
			
			/*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/form.jsp");
			rd.forward(request, response);*/
			
			WebUtil.forward(request, response, "/WEB-INF/form.jsp");
			
			
		} else if ("insert".equals(actionName)) {
			System.out.println("insert 진입");

			String lastName = request.getParameter("ln");
			String firstName = request.getParameter("fn");
			String email = request.getParameter("email");

			EmaillistVo vo = new EmaillistVo();
			vo.setLastName(lastName);
			vo.setFirstName(firstName);
			vo.setEmail(email);

			System.out.println(vo.toString());

			EmaillistDao dao = new EmaillistDao();
			dao.insert(vo);

			/*response.sendRedirect("/emaillist2/el?a=list");*/ // redirect 포워드와 방법이 다르다는 것!!!
			
			WebUtil.redirect(request, response, "/emaillist2/el?a=list");
			
		} else if ("list".equals(actionName)) {
			System.out.println("list 진입");

			EmaillistDao dao = new EmaillistDao();
			List<EmaillistVo> elist = dao.getList();

			request.setAttribute("elist", elist);
			
			/*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp"); // forward
			rd.forward(request, response);*/
			System.out.println(elist.toString());
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
			
		} else {
			System.out.println("잘못된 a값 처리 입니다.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
