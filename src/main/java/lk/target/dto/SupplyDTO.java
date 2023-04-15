package lk.target.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor


public class SupplyDTO {

    String code;
    Integer qty;
    Double recievedPrice;
}
