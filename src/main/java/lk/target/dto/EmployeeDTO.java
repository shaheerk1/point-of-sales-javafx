package lk.target.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeDTO {
    private String id;
    private String title;
    private String name;
    private String address;
    private String mobile;
    private String city;
    private String province;
    private double salary;
    private String role;
    private String username;
    private String password;

}
