package com.store.book.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.beans.Cart;
import com.store.book.utils.CartUtils;

public class DeleteFromCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            HttpSession session = req.getSession(true);
            System.out.println(session.getAttribute("cart"));
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                resp.sendRedirect("cart.jsp?error=404");
                return;
            }
            
            if (!CartUtils.deleteCart(cart.getId())) {
                resp.sendRedirect("cart.jsp?error=500");
                return;
            }
            
            session.removeAttribute("cart");
            resp.sendRedirect("home.jsp");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
