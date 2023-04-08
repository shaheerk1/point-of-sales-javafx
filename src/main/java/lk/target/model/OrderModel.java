package lk.target.model;

import lk.target.db.DBConnection;
import lk.target.dto.tm.CartDTO;
import lk.target.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
}
