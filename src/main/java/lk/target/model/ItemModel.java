package lk.target.model;

import lk.target.dto.ItemDTO;
import lk.target.dto.SupplyDTO;
import lk.target.dto.CartDTO;
import lk.target.dto.tm.ItemTM;
import lk.target.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public static ItemDTO searchItem(String itemCode) throws SQLException {
        String sql = "SELECT * FROM Item WHERE ItemCode = ?";

        ResultSet rs = CrudUtil.execute(sql,itemCode);

        if (rs.next()){
            ItemDTO itemDTO = new ItemDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(5),
                    rs.getDouble(4)
            );
            return itemDTO;
        }else {
            return null;
        }

    }

    public static String getNewItemCode() throws SQLException {
        String sql = "SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()){
            return splitItemId(rs.getString(1));
        }
        return splitItemId(null);
    }
    public static String splitItemId(String currentItemId) {
        if(currentItemId != null) {
            String[] strings = currentItemId.split("P0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "P0"+id;
        }
        return "O001";
    }

    public static Boolean saveItem(ItemDTO itemDTO) throws SQLException {
        String sql = "INSERT INTO Item(ItemCode, name, description,qtyonhand, unitprice) VALUES(?,?,?,?,?)";

       return CrudUtil.execute(sql, itemDTO.getItemCode(),itemDTO.getName(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getPrice());


    }

    public static Boolean updateItem(ItemDTO itemDTO) throws SQLException {
        String sql = "UPDATE Item SET name = ?, description = ?, qtyonhand = ?, unitprice = ? WHERE ItemCode = ?";


        return CrudUtil.execute(sql,itemDTO.getName(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getPrice(), itemDTO.getItemCode());

    }

    public static List<ItemTM> getAllItems() throws SQLException {
        String sql = "SELECT * FROM Item";
        ResultSet rs = CrudUtil.execute(sql);

        List<ItemTM> itemsList = new ArrayList<>();

            while (rs.next()){
                ItemTM itemTM = new ItemTM(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(5),
                        rs.getDouble(4)
                );
                itemsList.add(itemTM);

            }
            return itemsList;



    }

    public static Boolean deleteItem(String code) throws SQLException {
        String sql = "DELETE FROM Item WHERE ItemCode = ?";


        return CrudUtil.execute(sql,code);

    }

    public static List<String> getCodes() throws SQLException {

        List<String> codes = new ArrayList<>();

        String sql = "SELECT ItemCode FROM Item";
        ResultSet resultSet =CrudUtil.execute(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static ItemDTO searchById(String code) throws SQLException {
        String sql = "SELECT * FROM Item WHERE ItemCode = ?";



        ResultSet resultSet = CrudUtil.execute(sql, code);
        if(resultSet.next()) {
            return new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            );
        }
        return null;
    }

    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
        for (CartDTO dto : cartDTOList) {
            if(!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }
    public static boolean updateQtyForSupply(List<SupplyDTO> suppliesDTOList) throws SQLException {
        for (SupplyDTO dto : suppliesDTOList) {
            if(!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }
    private static boolean updateQty(CartDTO dto) throws SQLException {

        String sql = "UPDATE Item SET qtyOnHand = (qtyOnHand - ?) WHERE ItemCode = ?";

        return CrudUtil.execute(sql,dto.getQty(),dto.getCode());

    }
    private static boolean updateQty(SupplyDTO dto) throws SQLException {

        String sql = "UPDATE Item SET qtyOnHand = (qtyOnHand + ?) WHERE ItemCode = ?";

        return CrudUtil.execute(sql,dto.getQty(),dto.getCode());

    }
}
