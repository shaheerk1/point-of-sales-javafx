package lk.target.model;

import lk.target.db.DBConnection;
import lk.target.dto.SupplyDTO;
import lk.target.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplyModel {
    public static String generateNextId() throws SQLException {


        String sql = "SELECT ID FROM Supply ORDER BY ID DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "S0"+id;
        }
        return "S001";
    }

    public static boolean placeSupply(String supId, String supplierId,double total, List<SupplyDTO> suppliesDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = save(supId, supplierId, LocalDate.now(), total);
            if (isSaved) {
                boolean isUpdated = ItemModel.updateQtyForSupply(suppliesDTOList);
                if (isUpdated) {
                    boolean isOrderDetailSaved = SupplyDetailModel.save(supId, suppliesDTOList);
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

    public static boolean save(String id, String supId, LocalDate date, double tot) throws SQLException {
        String sql = "INSERT INTO Supply(Id, Time, SupplierID, isPaid, Amount) VALUES (?, NOW(), ?, ?, ?)";

        return CrudUtil.execute(sql,id,supId,0,tot );

    }}
