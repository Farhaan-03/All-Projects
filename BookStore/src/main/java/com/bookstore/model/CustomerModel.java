package com.bookstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    private Long customerId;
    private String name;

    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNo")
    private long phoneNo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("email")
    public String getMaskedEmail() {
        if (email != null && email.contains("@")) {
            return email.replaceAll("(?<=.{2}).(?=.*@)", "*");
        }
        return email;
    }

    @JsonProperty("phoneNo")
    public String getMaskedPhoneNumber() {
        if (phoneNo != 0 && phoneNo > 9999) {
            String phoneNumberStr = String.valueOf(phoneNo);
            return "**** **** " + phoneNumberStr.substring(phoneNumberStr.length() - 4);
        }
        return String.valueOf(phoneNo);
    }


}
