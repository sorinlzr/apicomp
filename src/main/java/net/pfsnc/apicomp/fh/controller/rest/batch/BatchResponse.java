package net.pfsnc.apicomp.fh.controller.rest.batch;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class BatchResponse {
    private BatchRequest.Request request;
    private ResponseEntity<?> response;

    public BatchResponse(BatchRequest.Request request, ResponseEntity<?> response) {
        this.request = request;
        this.response = response;
    }
}
