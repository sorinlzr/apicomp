package net.pfsnc.apicomp.fh.controller.rest.batch;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BatchRequest {
    private List<Request> requests;

    @Data
    public static class Request {
        private String method;
        private String endpoint;
        private Map<String, Object> body;
        private Map<String, Integer> params;
    }
}
