package event.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Retrieve the session if it exists, otherwise don't create a new one
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Invalidate the session to log out the user
            session.invalidate();
        }

        // Redirect to the home page after logout
        response.sendRedirect("index.html");
    }
}
