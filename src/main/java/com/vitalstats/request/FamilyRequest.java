package com.vitalstats.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRequest {

    private UUID userId;
    private String aadhaarNo;
    private String relationship;
    private String otp;
}
