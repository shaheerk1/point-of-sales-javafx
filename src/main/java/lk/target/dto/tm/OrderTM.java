package lk.target.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderTM {

    private String id;
    private String time;
    private String custId;
    private Button detail;
    private Button action;
}
