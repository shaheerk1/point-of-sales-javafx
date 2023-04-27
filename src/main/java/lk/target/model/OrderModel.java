package lk.target.model;

import javafx.scene.control.Button;
import lk.target.db.DBConnection;
import lk.target.dto.CartDTO;
import lk.target.dto.StarCustomerDTO;
import lk.target.dto.tm.OrderTM;
import lk.target.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static String generateNextOrderId() throws SQLException {


        String sql = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O0"+id;
        }
        return "O001";
    }

    public static boolean placeOrder(String oId, String cusId, List<CartDTO> cartDTOList) throws SQLException {
        Connection con = null;
            try {
                con = DBConnection.getInstance().getConnection();
    
                con.setAutoCommit(false);
    
                boolean isSaved = save(oId, cusId, LocalDate.now());
                if (isSaved) {
                    boolean isUpdated = ItemModel.updateQty(cartDTOList);
                    if (isUpdated) {
                        boolean isOrderDetailSaved = OrderDetailModel.save(oId, cartDTOList);
                        if (isOrderDetailSaved) {
                            con.commit();
                            return true;
                        }
                    }
                }
                return false;
            } catch (SQLException er) {
                er.printStackTrace();
                con.rollback();
                return false;
            } finally {
                con.setAutoCommit(true);
            }
    }
    public static boolean save(String oId, String cusId, LocalDate date) throws SQLException {
        String sql = "INSERT INTO Orders(OrderId, OrderTime, CustID) VALUES (?, NOW(), ?)";

        return CrudUtil.execute(sql,oId,cusId);

    }

    public static String getLastOrderId() throws SQLException {
        String sql = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<OrderTM> getAllIOrders() throws SQLException {
        String sql = "SELECT * FROM Orders";
        ResultSet rs = CrudUtil.execute(sql);

        List<OrderTM> ordList = new ArrayList<>();

        while (rs.next()){
            OrderTM orderTM = new OrderTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    new Button("Report"),
                    new Button("Remove")
            );
            ordList.add(orderTM);

        }
        return ordList;


    }

    public static boolean deleteOrder(String id) throws SQLException {
        String sql = "DELETE FROM orders WHERE OrderID = ?";


        return CrudUtil.execute(sql,id);
    }

    public static Double getMonthTotSale() throws SQLException {
        String sql = "SELECT SUM(od.OrderQTY * i.UnitPrice* (100 - od.Discount) / 100) AS TotalSales FROM Orders o JOIN OrderDetail od ON o.OrderID = od.OrderID JOIN Item i ON od.ItemCode = i.ItemCode WHERE YEAR(o.OrderTime) = YEAR(CURDATE()) AND MONTH(o.OrderTime) = MONTH(CURDATE())";
        ResultSet rs = CrudUtil.execute(sql);

        if(rs.next()){
            return rs.getDouble(1);
        }
        return null;

    }

    public static List<StarCustomerDTO> getBestCus() throws SQLException {
        String sql = "SELECT  c.CustID, c.CustName, SUM(od.OrderQTY * i.UnitPrice * (100 - od.Discount) / 100) AS TotalSpent FROM Orders o JOIN OrderDetail od ON o.OrderID = od.OrderID JOIN Item i ON od.ItemCode = i.ItemCode JOIN Customer c ON o.CustID = c.CustID GROUP BY c.CustID ORDER BY TotalSpent DESC LIMIT 5;";
        ResultSet rs = CrudUtil.execute(sql);

        List<StarCustomerDTO> strList = new ArrayList<>();

        while (rs.next()){
            StarCustomerDTO starCustomerDTO = new StarCustomerDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3)
            );
            strList.add(starCustomerDTO);

        }
        return strList;
    }

    public static Double getMonthTotExp() throws SQLException {

        String sql = "SELECT SUM(Amount) AS TotalExpense FROM Expense WHERE MONTH(Time) = MONTH(CURRENT_DATE()) AND YEAR(Time) = YEAR(CURRENT_DATE());";

        ResultSet rs = CrudUtil.execute(sql);

        if(rs.next()){
            return rs.getDouble(1);
        }
        return null;
    }

    public static Integer getTotalCount() throws SQLException {

        String sql = "SELECT COUNT(*) FROM Orders;";
        ResultSet rs = CrudUtil.execute(sql);

        if(rs.next()){
            return rs.getInt(1);
        }
        return null;
    }
}
