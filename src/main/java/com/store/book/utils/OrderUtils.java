package com.store.book.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.store.book.beans.Order;

public class OrderUtils {
    public static boolean PlaceOrder(String useremail, int cartId, String country, String province, String district) {
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO orders (cartId, country, province, district, owner) VALUES (?,?,?,?,?)")) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, province);
            preparedStatement.setString(4, district);
            preparedStatement.setString(5, useremail);

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Order> getOrders() throws Exception {
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders")) {
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<Order>();
            while (rs.next()) {
                int orderId = rs.getInt("id");
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM orders WHERE id = ?");
                ps2.setInt(1, orderId);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) { // Move the cursor to the first row
                    Order order = new Order();
                    order.setId(rs2.getInt("id"));
                    String owner = rs2.getString("owner");
                    order.setOwner(owner);
                    order.setCartId(rs2.getInt("cartid"));
                    order.setCountry(rs2.getString("country"));
                    order.setProvince(rs2.getString("province"));
                    order.setDistrict(rs2.getString("district"));
                    orders.add(order);
                }
                ps2.close();
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static boolean deliverOrder(int id) throws Exception{
        try(Connection con = DatabaseUtil.getConnection();
        PreparedStatement stmt = con.prepareStatement("DELETE FROM orders WHERE id = ?")){
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
