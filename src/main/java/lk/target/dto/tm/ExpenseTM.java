package lk.target.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseTM {
    private String id;
    private String description;
    private double amount;
    private String time;
    private String type;
    private String itemCode;
    private String supplyId;
    private String returnId;
}
