package iss.biblioteca.Domain;

import javax.persistence.*;

@Entity(name="User")
@Table(name="users")
public class User {
    //TODO fix this shit -> mapping -> update db -> userView -> RegisterController
    private String password;
    @Id
    private String username;
    private Integer age; // Added age field
    private String address;
    private String phone_number;
    private String CNP;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private UserType userType;

    public User(){

    }
    public User(String password, String username, Integer age, UserType userType) {
        this.password = password;
        this.username = username;
        this.age = age;
        this.userType = userType;
    }
    public User(String password, String username, Integer age, String adress, String phoneNumber, String CNP, String name, UserType userType) {
        this.password = password;
        this.username = username;
        this.age = age;
        this.address = adress;
        this.phone_number = phoneNumber;
        this.CNP = CNP;
        this.name = name;
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}