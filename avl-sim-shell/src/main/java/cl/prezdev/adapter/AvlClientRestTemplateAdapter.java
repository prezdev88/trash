package cl.prezdev.adapter;

import cl.prezdev.model.response.AddAvlResponse;
import cl.prezdev.model.response.ListAvlResponse;
import cl.prezdev.model.response.RemoveAllResponse;
import cl.prezdev.model.response.StartAllResponse;
import cl.prezdev.model.response.StatResponse;
import cl.prezdev.model.response.StopAvlResponse;
import cl.prezdev.port.AvlClientPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class AvlClientRestTemplateAdapter implements AvlClientPort {

    private RestTemplate restTemplate;

    @Value("${avl.client.base-url}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    @Override
    public AddAvlResponse addAvls(String type, int count) {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/api/avl/add")
                .queryParam("type", type)
                .queryParam("count", count)
                .toUriString();

        return restTemplate.postForObject(url, null, AddAvlResponse.class);
    }

    @Override
    public StatResponse getStats() {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/api/avl/stat")
                .toUriString();

        return restTemplate.getForObject(url, StatResponse.class);
    }
    
    @Override
    public ListAvlResponse listAvls(Integer page, Integer size) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(baseUrl + "/api/avl/list");
                
        if (page != null) {
            uriBuilder.queryParam("page", page);
        }
        if (size != null) {
            uriBuilder.queryParam("size", size);
        }
        
        String url = uriBuilder.toUriString();
        return restTemplate.getForObject(url, ListAvlResponse.class);
    }
    
    @Override
    public RemoveAllResponse removeAll() {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/api/avl/remove-all")
                .toUriString();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, RemoveAllResponse.class).getBody();
    }

    @Override
    public StartAllResponse startAll() {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/api/avl/start")
                .toUriString();

        return restTemplate.postForObject(url, null, StartAllResponse.class);
    }

    @Override
    public StopAvlResponse stopAll() {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/api/avl/stop")
                .toUriString();

        return restTemplate.postForObject(url, null, StopAvlResponse.class);
    }
}
