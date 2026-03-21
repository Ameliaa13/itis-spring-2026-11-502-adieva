package ru.itis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.AttestationRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttestationHashRequest {

    private AttestationRequest request;

    private String hash;

}