package com.demoqlsp.controller;

import com.demoqlsp.model.Category;
import com.demoqlsp.model.Product;
import com.demoqlsp.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "view":
                viewProduct(request, response);
                break;
            case "viewDetail":
                viewDetailProduct(request, response);
                break;
            case "create":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                showDeleteForm(request, response);
                break;
            default:
                viewProduct(request, response);
                break;
        }
    }

    private void searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String textSearch = request.getParameter("text");
        List<Product> products = productService.searchByName(textSearch);

        Map<Integer, String> category = new HashMap<>();
        for (int i=0; i<products.size(); i++) {
            category.put(products.get(i).getId(), productService.selectCategoryByProductId(products.get(i).getId()).getName());
        }
        request.setAttribute("category", category);

        request.setAttribute("listProduct", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void viewDetailProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Product product = productService.selectProduct(id);

        Category category = productService.selectCategoryByProductId(id);
        request.setAttribute("category", category);

        RequestDispatcher dispatcher = request.getRequestDispatcher("view.jsp");
        request.setAttribute("product", product);
        dispatcher.forward(request, response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Product product = productService.selectProduct(id);

        Category category = productService.selectCategoryByProductId(id);
        request.setAttribute("category", category);

        RequestDispatcher dispatcher = request.getRequestDispatcher("delete.jsp");
        request.setAttribute("product", product);
        dispatcher.forward(request, response);
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Product product = productService.selectProduct(id);

        List<Category> categories = productService.getAllCategory();
        request.setAttribute("category", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        request.setAttribute("product", product);
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = productService.getAllCategory();
        request.setAttribute("category", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("create.jsp");
        dispatcher.forward(request, response);
    }

    private void viewProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.selectAllUsers();

        Map<Integer, String> category = new HashMap<>();
        for (int i=0; i<products.size(); i++) {
            category.put(products.get(i).getId(), productService.selectCategoryByProductId(products.get(i).getId()).getName());
        }
        request.setAttribute("category", category);

        request.setAttribute("listProduct", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        try {
            switch (action) {
                case "view":
                    viewProduct(request, response);
                    break;
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "search":
                    searchByName(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.deleteProduct(id);

        viewProduct(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        String name = request.getParameter("name");
        Float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        String categoryName = request.getParameter("category");
        int category = 0;

        List<Category> categories = productService.getAllCategory();
        for (Category categorys: categories) {
            if (categorys.getName().equals(categoryName)) {
                category = categorys.getCateId();
                break;
            }
        }

        Product product = new Product(id, name, price, quantity, color, description, category);
        productService.updateProduct(product);

        RequestDispatcher dispatcher = request.getRequestDispatcher("product?action=view");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        String categoryName = request.getParameter("category");
        int category = 0;

        List<Category> categories = productService.getAllCategory();
        for (Category categorys: categories) {
            if (categorys.getName().equals(categoryName)) {
                category = categorys.getCateId();
                break;
            }
        }

        Product product = new Product(name, price, quantity, color, description, category);
        productService.insertUser(product);

        RequestDispatcher dispatcher = request.getRequestDispatcher("product?action=view");
        dispatcher.forward(request, response);
    }
}
