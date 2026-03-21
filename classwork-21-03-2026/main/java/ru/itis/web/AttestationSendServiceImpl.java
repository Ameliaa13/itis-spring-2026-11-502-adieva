package ru.itis.web;

import lombok.SneakyThrows;
import okhttp3.*;
import ru.itis.AttestationHashRequest;
import ru.itis.AttestationResponse;
import ru.itis.AttestationSendService;
import tools.jackson.databind.ObjectMapper;

public class AttestationSendServiceImpl implements AttestationSendService {

    private static final OkHttpClient client;
    private static final ObjectMapper objectMapper;

    static {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }
    @Override
    @SneakyThrows
    public AttestationResponse sendAttestation(AttestationHashRequest request) {
        String json = objectMapper.writeValueAsString(request);

        Request rq = new Request.Builder()
                .url("http://185.221.160.131:81/attestation")
                .post(RequestBody.create(json, MediaType.parse("application/json; charset=utf-8")))
                .build();

        try (Response response = client.newCall(rq).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, AttestationResponse.class);
        }
    }
}
