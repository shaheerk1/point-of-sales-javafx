package lk.target.dto;

public class EmployeeDTO {
    private String id;
    private String title;
    private String name;
    private String address;
    private String mobile;

    public EmployeeDTO(String id, String title, String name, String address, String mobile, String city, String province, Double salary, String role, String username, String password) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.city = city;
        this.province = province;
        this.salary = salary;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    private String city;
    private String province;
    private Double salary;
    private String role;

    public EmployeeDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String username;
    private String password;

}
