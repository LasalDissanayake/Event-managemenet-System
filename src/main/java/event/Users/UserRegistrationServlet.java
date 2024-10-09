package event.Users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.addEvent.DBConnect;

@WebServlet("/UserRegistrationServlet")
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Retrieve form parameters
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		String repass = request.getParameter("re_pass");

		RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");

		// Validate input fields
		if (uname == null || uname.trim().isEmpty()) {
			request.setAttribute("status", "invalidName");
			dispatcher.forward(request, response);
			return;
		}
		if (uemail == null || uemail.trim().isEmpty()) {
			request.setAttribute("status", "invalidEmail");
			dispatcher.forward(request, response);
			return;
		}
		if (upwd == null || upwd.trim().isEmpty()) {
			request.setAttribute("status", "invalidUpwd");
			dispatcher.forward(request, response);
			return;
		}
		if (!upwd.equals(repass)) {
			request.setAttribute("status", "invalidConfirmPass");
			dispatcher.forward(request, response);
			return;
		}
		if (umobile == null || umobile.trim().isEmpty()) {
			request.setAttribute("status", "invalidMobile");
			dispatcher.forward(request, response);
			return;
		}
		if (umobile.length() != 10) {
			request.setAttribute("status", "invalidMobileLength");
			dispatcher.forward(request, response);
			return;
		}

		Connection con = null;

		try {
			// Get connection from DBConnect utility class
			con = DBConnect.getConnection();
			if (con != null) {
				PreparedStatement pst = con.prepareStatement("INSERT INTO user(name, email, password, mobile) VALUES(?,?,?,?)");

				// Set the parameters
				pst.setString(1, uname);
				pst.setString(2, uemail);
				pst.setString(3, upwd);
				pst.setString(4, umobile);

				// Execute the query and check if row was inserted
				int rowCount = pst.executeUpdate();

				if (rowCount > 0) {
					request.setAttribute("status", "success");
				} else {
					request.setAttribute("status", "failed");
				}

				// Forward to the same page with status
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("status", "dbError");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("status", "exception");
			dispatcher.forward(request, response);
		} finally {
			// Close the connection if it was opened
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
