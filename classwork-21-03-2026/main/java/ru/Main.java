package ru;

import ru.itis.AttestationHashRequest;
import ru.itis.AttestationRequest;
import ru.itis.AttestationResponse;
import ru.itis.RequestHashService;
import ru.itis.SecretCodeService;
import ru.itis.security.RequestHashServiceImpl;
import ru.itis.security.SecretCodeServiceImpl;
import ru.itis.InputService;
import ru.itis.SystemPropertiesService;
import ru.itis.service.InputServiceScannerImpl;
import ru.itis.service.SystemPropertiesServiceImpl;
import ru.itis.AttestationSendService;
import ru.itis.web.AttestationSendServiceImpl;

public class Main {

    public static final InputService inputService;
    public static final SystemPropertiesService systemPropertiesService;
    public static final SecretCodeService secretCodeService;
    public static final RequestHashService requestHashService;
    public static final AttestationSendService attestationSendService;

    static {
        inputService = new InputServiceScannerImpl();
        systemPropertiesService = new SystemPropertiesServiceImpl();
        secretCodeService = new SecretCodeServiceImpl();
        requestHashService = new RequestHashServiceImpl();
        attestationSendService = new AttestationSendServiceImpl();
    }

    public static void main(String[] args) {
        System.out.println("Started process of registration");
        String fio = inputService.inputFio();
        AttestationRequest request = new AttestationRequest();
        request.setFio(fio);

        System.out.println("Adding secret word...");
        String secret = secretCodeService.getSecretCode();
        request.setSecret(secret);

        System.out.println("Adding os info...");
        request.setOsName(systemPropertiesService.getOsName());
        request.setOsVersion(systemPropertiesService.getOsVersion());
        request.setOsArch(systemPropertiesService.getOsArch());
        request.setUserName(systemPropertiesService.getUserName());

        System.out.println("Signing request...");
        AttestationHashRequest hashRequest = requestHashService.addSignature(request);

        System.out.println("Sending request...");
        AttestationResponse rs = attestationSendService.sendAttestation(hashRequest);

        if(rs.isValid() && rs.isValid2()) {
            System.out.println("Registration ok, success");
        } else {
            System.out.println("Sent but with problems");
        }
    }
}
