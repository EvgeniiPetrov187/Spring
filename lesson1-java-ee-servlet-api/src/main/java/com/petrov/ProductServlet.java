package com.petrov;

import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        String path = req.getContextPath() + req.getServletPath()+"/";

        //отображение таблицы с продуктами
        if (req.getPathInfo() == null) {
            List<Product> productList = productRepository.findAll();
            wr.println("<table border=1>");
            wr.println("<tr><td>ID</td><td>Name of Product</td><td>Cost pro kilo</td></tr>");
            for (Product product : productList) {
                wr.println("<tr><td>" + product.getId() + "</td>");
                wr.println("<td>");

                // 2. В таблице продуктов значение в колонке имени должно быть гиперссылкой
                // на страницу продукта
                wr.println("<a href='" + path + product.getId() + "'/>" + product.getName() + "</a>");
                wr.println("</td>");
                wr.println("<td>" + product.getCoast().toString() + "</td><tr>");
            }
            wr.println("</table>");
        } else {
            // 1. Добавить страницу с информацией о конкретном продукте.
            // На неё должна вести ссылка вида /product/<id продукта>.
            String pathInfo = req.getPathInfo().replace("/", "");
            Long id = Long.parseLong(pathInfo);
            Product product = productRepository.findById(id);
            wr.println("<h1>" + product.getName() + "</h1>");
            wr.println("<h2>costs " + product.getCoast() + " rubbles pro kilo<h2>");
        }
    }

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }
}
