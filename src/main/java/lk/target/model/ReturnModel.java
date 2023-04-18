package lk.target.model;

import lk.target.dto.ReturnDTO;
import lk.target.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnModel {
    public static String generateNextId() throws SQLException {


        String sql = "SELECT ID FROM returnItem ORDER BY ID DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("R0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "R0"+id;
        }
        return "R001";
    }

    public static Boolean saveItem(ReturnDTO returnDTO) throws SQLException {
        String sql = "INSERT INTO ReturnItem(id, ItemCode, OrderId, QTY, Time) VALUES(?,?,?,?, NOW())";

        return CrudUtil.execute(sql,returnDTO.getId(),returnDTO.getItemCode(), returnDTO.getOrderId(), returnDTO.getQty());

    }
}
