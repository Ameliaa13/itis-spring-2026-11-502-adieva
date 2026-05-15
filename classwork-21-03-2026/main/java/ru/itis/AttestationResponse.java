package ru.itis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttestationResponse {

    private String fio;

    private Instant createdAt;

    private boolean valid;
    private boolean valid2;

    private String osName;
    private String osVersion;
    private String osArch;
    private String userName;

}
