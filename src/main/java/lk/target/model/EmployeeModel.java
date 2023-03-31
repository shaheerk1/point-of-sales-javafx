package lk.target.model;

import lk.target.dto.EmployeeDTO;
import lk.target.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {


    public static EmployeeDTO checkCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE username = ?";

        ResultSet rs = CrudUtil.execute(sql,username);

        if (rs.next()){
            EmployeeDTO emp = new EmployeeDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDouble(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getNString(11)
            );
            return emp;
        }else {
            return null;
        }

    }
}
