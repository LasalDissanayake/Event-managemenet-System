package event.Users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import event.addEvent.DBConnect;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Retrieve form parameters
		String uemail = request.getParameter("username");
		String upwd = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;

		// Validate input fields
		if (uemail == null || uemail.trim().isEmpty()) {
			request.setAttribute("status", "invalidEmail");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		if (upwd == null || upwd.trim().isEmpty()) {
			request.setAttribute("status", "invalidPassword");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		Connection con = null;

		try {
			// Get the connection from DBConnect utility class
			con = DBConnect.getConnection();

			// Prepare SQL statement
			PreparedStatement pst = con.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);

			// Execute query
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				// Set session attributes for the logged-in user
				session.setAttribute("name", rs.getString("name"));
				session.setAttribute("email", rs.getString("email"));
				session.setAttribute("id", rs.getString("id"));

				// Redirect to user home page
				dispatcher = request.getRequestDispatcher("userHome.jsp");
			} else {
				// If credentials are incorrect, redirect to login page with error
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}

			// Forward the request to the appropriate page
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("status", "dbError");
			dispatcher = request.getRequestDispatcher("login.jsp");
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
