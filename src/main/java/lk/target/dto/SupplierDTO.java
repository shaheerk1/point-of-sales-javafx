package lk.target.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierDTO {
    private String id;
    private String title;
    private String name;
    private String address;
    private String mobile;
    private String city;
    private String province;
    private String country;

}
