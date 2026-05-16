package org.example.Task1;

public class Address {
    @NotNull
    @Size(min = 1, max = 50)
    private String street;

    @NotNull
    @Size(min = 1, max = 30)
    private String city;

    @NotNull
    @Size(min = 4, max = 10)
    private String zipCode;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getZipCode() { return zipCode; }
}