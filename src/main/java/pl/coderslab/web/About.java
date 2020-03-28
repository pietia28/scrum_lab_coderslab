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


@WebServlet("/about")
public class About extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        getServletContext().getRequestDispatcher("/about.jsp").forward(request, response);
    }
}