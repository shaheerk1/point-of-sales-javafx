package lk.target.model;

import lk.target.db.DBConnection;
import lk.target.dto.tm.CartDTO;
import lk.target.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String oId, List<CartDTO> cartDTOList) throws SQLException {
        for(CartDTO dto :  cartDTOList) {
            if(!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, CartDTO dto) throws SQLException {

        String sql = "INSERT INTO OrderDetail(OrderId, itemCode, OrderQty, Discount) VALUES (?, ?, ?, ?)";

        return CrudUtil.execute(sql, oId,dto.getCode(),dto.getQty(), 0 );


    }
}
