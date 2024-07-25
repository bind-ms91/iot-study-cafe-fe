package bind.iotstudycafe.commons.exampleDomain.service;

import bind.iotstudycafe.exampleDomain.domain.ExampleDomain;
import bind.iotstudycafe.exampleDomain.dto.ExampleDomainSave;
import bind.iotstudycafe.exampleDomain.service.ExampleDomainServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class ExampleDomainServiceTest {

    @Autowired
    private WebClient iotCafeWebClient;

    private MockWebServer mockWebServer1;

//    private MockWebServer mockWebServer2;

    @Autowired
    private ExampleDomainServiceImpl exampleDomainService;



    @BeforeEach
    void setUp() throws IOException {
        mockWebServer1 = new MockWebServer();
        mockWebServer1.start();

//        mockWebServer2 = new MockWebServer();
//        mockWebServer2.start();

        iotCafeWebClient = WebClient.builder()
                .baseUrl(mockWebServer1.url("/").toString())
                .build();

        int mockWebServerPort = mockWebServer1.getPort();
        log.info("mockWebServerPort = {}", mockWebServerPort);

//        server1WebClient = WebClient.builder()
//                .baseUrl(mockWebServer2.url("/").toString())
//                .build();

        exampleDomainService = new ExampleDomainServiceImpl(iotCafeWebClient, null);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer1.shutdown();
//        mockWebServer2.shutdown();
    }

    @Test
    void findByIdToEntity() throws JsonProcessingException {

        //given
        ExampleDomain exampleDomain = new ExampleDomain();

        exampleDomain.setId(1L);
        exampleDomain.setLoginId("ms91");
        exampleDomain.setPassword("9999");
        exampleDomain.setName("Cho");
        exampleDomain.setAge(23);

        ObjectMapper objectMapper = new ObjectMapper();

        //when
        mockWebServer1.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(exampleDomain)));

        Mono<ResponseEntity<ExampleDomain>> responseMono = exampleDomainService.findByIdToEntity(1L);

        //then
        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    ExampleDomain body = response.getBody();
                    log.info("body={}", body);
                    Assertions.assertThat(exampleDomain).isEqualTo(body);
                })
                .verifyComplete();
    }

    @Test
    void testFindByIdBodyToMono() throws JsonProcessingException {

        //given
        ExampleDomain exampleDomain = new ExampleDomain();

        exampleDomain.setId(1L);
        exampleDomain.setLoginId("ms91");
        exampleDomain.setPassword("9999");
        exampleDomain.setName("Cho");
        exampleDomain.setAge(23);

        ObjectMapper objectMapper = new ObjectMapper();

        //when
        mockWebServer1.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(exampleDomain)));

        Mono<ExampleDomain> result = exampleDomainService.findByIdBodyToMono(1L);

        //when
        StepVerifier.create(result)
                .expectNextMatches(domain -> {
                    log.info("body={}", domain);
                    Assertions.assertThat(exampleDomain).isEqualTo(domain);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testSaveToEntity() throws JsonProcessingException {

        //given
        ExampleDomainSave exampleDomainSave = new ExampleDomainSave();
        exampleDomainSave.setLoginId("ms91");
        exampleDomainSave.setPassword("9999");
        exampleDomainSave.setName("Cho");
        exampleDomainSave.setAge(23);

        ObjectMapper objectMapper = new ObjectMapper();

        //when
        mockWebServer1.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(exampleDomainSave)));

        Mono<ResponseEntity<ExampleDomain>> result = exampleDomainService.saveToEntity(exampleDomainSave);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    ExampleDomain body = response.getBody();
                    log.info("body={}", body);
                    Assertions.assertThat(body.getLoginId()).isEqualTo(exampleDomainSave.getLoginId());
                    Assertions.assertThat(body.getPassword()).isEqualTo(exampleDomainSave.getPassword());
                    Assertions.assertThat(body.getName()).isEqualTo(exampleDomainSave.getName());
                    Assertions.assertThat(body.getAge()).isEqualTo(exampleDomainSave.getAge());
                })
                .verifyComplete();
    }

}