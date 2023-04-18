package lk.target.model;

import lk.target.dto.ExpenseDTO;
import lk.target.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseModel {
    public static Boolean save(ExpenseDTO expenseDTO) throws SQLException {
        String sql;
        boolean saved = false;
        switch (expenseDTO.getType().toLowerCase()){
            case "repair" :
                sql = "INSERT INTO Expense(Description,Amount,Time,Type,ItemCode) VALUES(?,?,NOW(),?,?)";
                saved =  CrudUtil.execute(sql,expenseDTO.getDescription(),expenseDTO.getAmount(),expenseDTO.getType(),expenseDTO.getItemCode());
                break;
            case "supply" :
                sql = "INSERT INTO Expense(Description,Amount,Time,Type,SupplyID) VALUES(?,?,NOW(),?,?)";
                saved = CrudUtil.execute(sql,expenseDTO.getDescription(),expenseDTO.getAmount(),expenseDTO.getType(),expenseDTO.getSupplyID());
                break;
            case "return" :
                sql = "INSERT INTO Expense(Description,Amount,Time,Type,ReturnID) VALUES(?,?,NOW(),?,?)";
                saved = CrudUtil.execute(sql,expenseDTO.getDescription(),expenseDTO.getAmount(),expenseDTO.getType(),expenseDTO.getReturnID());
                break;
            default:
                sql = "INSERT INTO Expense(Description,Amount,Time,Type) VALUES(?,?,NOW(),?)";
                saved = CrudUtil.execute(sql,expenseDTO.getDescription(),expenseDTO.getAmount(),expenseDTO.getType());
                break;
        }
        return saved;



    }

//    public static String generateNextId() throws SQLException {
//
//
//        String sql = "SELECT ID FROM expense ORDER BY ID DESC LIMIT 1";
//
//        ResultSet resultSet = CrudUtil.execute(sql);
//        if(resultSet.next()) {
//            return splitId(resultSet.getString(1));
//        }
//        return splitId(null);
//    }
//
//    public static String splitId(String currentOrderId) {
//        if(currentOrderId != null) {
//            String[] strings = currentOrderId.split("R0");
//            int id = Integer.parseInt(strings[1]);
//            id++;
//
//            return "R0"+id;
//        }
//        return "R001";
//    }

}
