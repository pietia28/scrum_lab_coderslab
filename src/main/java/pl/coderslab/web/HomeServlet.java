package pl.coderslab.web;

import pl.coderslab.dao.BookDao;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.model.Book;
import pl.coderslab.model.DayName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Do not change servlet address !!!
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.findAll();
        System.out.println(books);

        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNames = dayNameDao.findAll();
        System.out.println(dayNames);

        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
