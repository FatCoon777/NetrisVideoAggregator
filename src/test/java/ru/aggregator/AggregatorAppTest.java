package ru.aggregator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import ru.AggregatorApp;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootTest(classes = AggregatorApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ConfigurationTest.class)
public class AggregatorAppTest {
    private final TestRestTemplate restTemplate;

    private final String restApiUrl;

    private final ObjectMapper objectMapper;

    @Autowired
    public AggregatorAppTest(TestRestTemplate restTemplate,
                             @Value("${respApi.url}") String restApiUrl,
                             ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.restApiUrl = restApiUrl;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testAggregateData() throws InterruptedException, IOException {
        Thread.sleep(3000);
        String aggragatedData = restTemplate.getForObject(restApiUrl, String.class);
        JsonNode aggreagatedNode = objectMapper.readTree(aggragatedData);
        JsonNode fileNode = objectMapper.readTree(Paths.get("src/test/resources/result.json").toFile());
        Assertions.assertEquals(aggreagatedNode, fileNode);
    }
}
