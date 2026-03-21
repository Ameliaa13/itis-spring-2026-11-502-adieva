package ru.itis.security;

import org.mindrot.jbcrypt.BCrypt;
import ru.itis.AttestationHashRequest;
import ru.itis.AttestationRequest;
import ru.itis.RequestHashService;

public class RequestHashServiceImpl implements RequestHashService {
    @Override
    public AttestationHashRequest addSignature(AttestationRequest request) {
        String str = request.toString();
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(str, salt);

        return AttestationHashRequest.builder()
                .hash(hash)
                .request(request)
                .build();
    }
}
