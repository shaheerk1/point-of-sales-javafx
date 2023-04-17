package lk.target.model;

import lk.target.dto.SupplyDTO;
import lk.target.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class SupplyDetailModel {
    public static boolean save(String Id, List<SupplyDTO> supplyDTOList) throws SQLException {
        for(SupplyDTO dto :  supplyDTOList) {
            if(!save(Id, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String Id, SupplyDTO dto) throws SQLException {

        String sql = "INSERT INTO SupplyDetail(SupplyID, itemCode, Qty) VALUES (?, ?, ?)";

        return CrudUtil.execute(sql, Id,dto.getCode(),dto.getQty());


    }
}
