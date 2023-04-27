package lk.target.model;

import javafx.scene.control.Button;
import lk.target.dto.ExpenseDTO;
import lk.target.dto.tm.ExpenseTM;
import lk.target.dto.tm.ReturnTM;
import lk.target.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<ExpenseTM> getAll() throws SQLException {
        String sql = "SELECT * FROM Expense";
        ResultSet rs = CrudUtil.execute(sql);

        List<ExpenseTM> expenseList = new ArrayList<>();

        while (rs.next()){
            ExpenseTM expenseTM = new ExpenseTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
            );
            expenseList.add(expenseTM);

        }
        return expenseList;
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
