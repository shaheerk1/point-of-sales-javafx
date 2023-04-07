package lk.target.model;

import lk.target.db.DBConnection;
import lk.target.dto.CustomerDTO;
import lk.target.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT CustID FROM Customer";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

}
