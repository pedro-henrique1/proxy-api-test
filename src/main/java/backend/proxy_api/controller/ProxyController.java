package backend.proxy_api.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/proxy")
@CrossOrigin(origins = "*")
@Slf4j
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/aeroportos")
    public ResponseEntity<String> getAeroportos(@RequestParam String q) {
        String apiUrl = "https://buscamilhas.mock.gralmeidan.dev/aeroportos?q=" + q;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
    @PostMapping("/pass")
    public ResponseEntity<String> postTicket(@RequestBody Map<String, Object> request) {
        String apiUrl = "https://buscamilhas.mock.gralmeidan.dev/busca/criar";

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao chamar a API externa: " + e.getMessage());
        }
    }


    @GetMapping("/busca/{id}")
    public ResponseEntity<String> getBusca(@PathVariable String id) {
        String apiUrl = "https://buscamilhas.mock.gralmeidan.dev/busca/" + id;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        log.info("Busca: " + response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


}
