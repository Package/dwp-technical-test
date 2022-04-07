package uk.gov.dwp.users.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String ipAddress;

    private double latitude;

    private double longitude;
}
