package ru.itis;

import ru.itis.AttestationHashRequest;
import ru.itis.AttestationRequest;

public interface RequestHashService {

    AttestationHashRequest addSignature(AttestationRequest request);

}
