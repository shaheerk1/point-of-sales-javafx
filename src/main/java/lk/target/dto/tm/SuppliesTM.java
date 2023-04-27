package lk.target.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SuppliesTM {
    private String id;
    private String time;
    private String supId;
    private Boolean isPaid;
    private Double Amount;
    private Button detail;
    private Button action;
}
