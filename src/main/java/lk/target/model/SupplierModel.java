package lk.target.model;

import lk.target.dto.CustomerDTO;
import lk.target.dto.SupplierDTO;
import lk.target.dto.tm.CustomerTM;
import lk.target.dto.tm.SupplierTM;
import lk.target.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static String getNextCode() throws SQLException {
        String sql = "SELECT ID FROM Supplier ORDER BY ID DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()){
            return splitId(rs.getString(1));
        }
        return splitId(null);
    }

    public static String splitId(String currentItemId) {
        if(currentItemId != null) {
            String[] strings = currentItemId.split("SR0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "SR0"+id;
        }
        return "SR001";
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT ID FROM Supplier";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static SupplierDTO searchSupplier(String supplierCode) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE Id = ?";

        ResultSet rs = CrudUtil.execute(sql,supplierCode);

        if (rs.next()){
            SupplierDTO supplier = new SupplierDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)

            );
            return supplier;
        }else {
            return null;
        }
    }

    public static Boolean update(SupplierDTO supplierDTO) throws SQLException {
        String sql = "UPDATE Supplier SET Title = ?, Name= ?, Address= ?, Mobile= ?, city= ?, province= ?, Country = ? WHERE ID = ?";


        return CrudUtil.execute(sql,supplierDTO.getTitle(),supplierDTO.getName(),supplierDTO.getAddress(),supplierDTO.getMobile(),supplierDTO.getCity(),supplierDTO.getProvince(),supplierDTO.getCountry(), supplierDTO.getId());

    }

    public static Boolean delete(String text) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE Id = ?";


        return CrudUtil.execute(sql,text);
    }

    public static Boolean save(SupplierDTO supplierDTO) throws SQLException {
        String sql = "INSERT INTO Supplier(Id, Title, Name, Address, Mobile, city, province, country) VALUES(?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql, supplierDTO.getId(),supplierDTO.getTitle(),supplierDTO.getName(),supplierDTO.getAddress(),supplierDTO.getMobile(),supplierDTO.getCity(),supplierDTO.getProvince(),supplierDTO.getCountry());

    }

    public static List<SupplierTM> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";
        ResultSet rs = CrudUtil.execute(sql);

        List<SupplierTM> supList = new ArrayList<>();

        while (rs.next()){
            SupplierTM supplierTM = new SupplierTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(5),
                    rs.getString(4)
            );
            supList.add(supplierTM);

        }
        return supList;
    }

}
