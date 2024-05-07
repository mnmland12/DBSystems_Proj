package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Services.*;
import DataAccess.*;
import DataModel.EmployeeModel;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	private EmployeeService employeeService;
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException{
		System.out.println("in init");
		try{
			Class.forName("org.postgresql.Driver");
			Connection conn = DatabaseConnection.getInstance();
			EmployeeConnect ec = new EmployeeConnect(conn);
			employeeService = new EmployeeService(ec);
			System.out.println("end of init");
		}catch(ClassNotFoundException e){
			System.out.println("postgresql driver not found");
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		System.out.println("in post");
		int empID = Integer.valueOf(request.getParameter("employeeID"));	
		System.out.println(empID);
		boolean isValidEmployeeID = employeeService.validateEmployeeID(empID);

		HttpSession session = request.getSession();

		
		if(isValidEmployeeID) {
			//get employee info
			EmployeeModel employee = employeeService.getEmployeeByID(empID);
			String empFirstName = employee.getFirstName();
			String empLastName = employee.getLastName();
			String access = employee.getPosition();
			session.setAttribute("firstName", empFirstName);
			session.setAttribute("lastName", empLastName);
			session.setAttribute("position", access);
			session.setAttribute("empID", empID);
			//String dbPath = request.getContextPath() + "/static/dashboard.html";
			response.sendRedirect("homepage.jsp");
		}else {

			//String loginPath = request.getContextPath() + "/static/login.html?error=true";
			response.sendRedirect("index.jsp");
		}
	}
}
