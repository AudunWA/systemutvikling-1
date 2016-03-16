package no.ntnu.iie.stud.cateringstorm.entities;

/**
 * Created by Audun on 10.03.2016.
 */
public class Address {
    private String streetName;
    private int houseNumber;
    private String apartmentName;
    private String postCode;
    private String cityName;
    private String country;

    public Address(String streetName, int houseNumber, String apartmentName, String postCode, String cityName, String country) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.apartmentName = apartmentName;
        this.postCode = postCode;
        this.cityName = cityName;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public static Address parseAddress(String s) {
        String[] splitted = s.split(" ");
        if(splitted.length > 2) {
            throw new IllegalArgumentException("Address too short");
        }

        String streetName = splitted[0];
        int houseNumber = -1;
        for (int i = 1; i < splitted.length; i++) {
            if(Character.isDigit(splitted[i].charAt(0))) {
                // Street name is done, this is house number
                houseNumber = Integer.parseInt(splitted[i]);
            } else {
                // Part of the street name
                streetName += " " + splitted[i];
            }
        }
        return new Address(streetName, houseNumber, null, null, null, null);
    }
}
