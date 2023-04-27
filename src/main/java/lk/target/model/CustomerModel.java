package lk.target.model;

import lk.target.db.DBConnection;
import lk.target.dto.CustomerDTO;
import lk.target.dto.ItemDTO;
import lk.target.dto.tm.CustomerTM;
import lk.target.dto.tm.ItemTM;
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

    public static CustomerDTO searchCustomer(String cusCode) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CustId = ?";

        ResultSet rs = CrudUtil.execute(sql,cusCode);

        if (rs.next()){
            CustomerDTO customer = new CustomerDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            return customer;
        }else {
            return null;
        }
    }

    public static Boolean saveItem(CustomerDTO customerDTO) throws SQLException {
        String sql = "INSERT INTO Customer(CustId, CustTitle, CustName, CustAddress, CustMobile, city, province) VALUES(?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql, customerDTO.getId(),customerDTO.getTitle(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getMobile(),customerDTO.getCity(),customerDTO.getProvince());

    }

    public static String getNextCode() throws SQLException {
        String sql = "SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()){
            return splitItemId(rs.getString(1));
        }
        return splitItemId(null);
    }
    public static String splitItemId(String currentItemId) {
        if(currentItemId != null) {
            String[] strings = currentItemId.split("C0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "C0"+id;
        }
        return "C001";
    }

    public static Boolean update(CustomerDTO customerDTO) throws SQLException {
        String sql = "UPDATE Customer SET CustTitle = ?, CustName= ?, CustAddress= ?, CustMobile= ?, city= ?, province= ? WHERE CustID = ?";


        return CrudUtil.execute(sql,customerDTO.getTitle(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getMobile(),customerDTO.getCity(),customerDTO.getProvince(), customerDTO.getId());

    }

    public static Boolean delete(String text) throws SQLException {
        String sql = "DELETE FROM Customer WHERE CustId = ?";


        return CrudUtil.execute(sql,text);
    }

    public static List<CustomerTM> getAll() throws SQLException {
        String sql = "SELECT * FROM Customer";
        ResultSet rs = CrudUtil.execute(sql);

        List<CustomerTM> cusList = new ArrayList<>();

        while (rs.next()){
            CustomerTM customerTM = new CustomerTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(5),
                    rs.getString(4)
            );
            cusList.add(customerTM);

        }
        return cusList;
    }

    public static List<String> getIds(String text) throws SQLException {
        String sql = "SELECT CustID FROM Customer WHERE CustName LIKE ?";

        text = "%"+text+"%";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql, text);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static Integer getTotalCustomers() throws SQLException {

        String sql = "SELECT COUNT(*) FROM Customer;";
        ResultSet rs = CrudUtil.execute(sql);

        if(rs.next()){
            return rs.getInt(1);
        }
        return null;
    }
}
