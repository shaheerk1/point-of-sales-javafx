package lk.target.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    String code;
    Integer qty;
    Double unitPrice;
}
