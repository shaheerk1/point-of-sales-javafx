package lk.target.dto.tm;

import javafx.scene.control.Label;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockTM {
    private String id;
    private String name;
    private Integer qtyOnHand;
    private Label status;
}
