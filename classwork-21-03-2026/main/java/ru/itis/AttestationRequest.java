package ru.itis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttestationRequest {

    private String secret;
    private String fio;
    private String osName;
    private String osVersion;
    private String osArch;
    private String userName;

}
