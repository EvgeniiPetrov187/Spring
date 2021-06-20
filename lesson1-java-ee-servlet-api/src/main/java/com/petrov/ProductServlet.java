package com.petrov;

import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/products")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList =  productRepository.findAll();


        //отображение таблицы с продуктами
        resp.getWriter().println("<table border=1>");
        resp.getWriter().println("<tr><td>Name of Product</td><td>Cost pro kilo</td></tr>");
        for (Product product : productList) {
            resp.getWriter().print("<tr><td>"+product.getName() + "</td>");
            resp.getWriter().println("<td>" + product.getCoast().toString()+"</td><tr>");
        }
        resp.getWriter().println("</table>");
    }

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }
}
