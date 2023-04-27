package lk.target.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReturnTM {
    private String id;
    private String itemCode;
    private String orderId;
    private Integer qty;
    private String time;
    private Button action;
}
