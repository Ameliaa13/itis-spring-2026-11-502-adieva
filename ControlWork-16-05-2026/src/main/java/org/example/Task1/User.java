package org.example.Task1;

public class User {
    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @Min(18)
    @Max(120)
    private int age;

    @NotNull
    @Email
    private String email;

    @Valid
    private Address address;

    public User(String name, int age, String email, Address address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public Address getAddress() { return address; }
}