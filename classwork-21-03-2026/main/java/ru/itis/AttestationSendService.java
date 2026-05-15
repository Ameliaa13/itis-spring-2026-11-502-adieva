package ru.itis;

import ru.itis.AttestationHashRequest;
import ru.itis.AttestationResponse;

public interface AttestationSendService {

    AttestationResponse sendAttestation(AttestationHashRequest request);

}
