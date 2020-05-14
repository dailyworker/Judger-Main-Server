package com.qt.problem;

import com.qt.AcceptanceTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProblemAcceptanceTest {

    @Autowired
    private WebTestClient webTestClient;

    private String problemId;

    @BeforeEach
    @DisplayName("pdf 파일, 문제 저장 테스트")
    void createProblem() {
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3}) {
            @Override
            public String getFilename() {
                return "test.pdf";
            }
        };

        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri("/problems")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file)
                        .with("name", "test")
                        .with("timeLimit", 1d)
                        .with("memoryLimit", 1d))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader().valueMatches("Location", "/problems/[1-9]+[0-9]*");

        problemId = AcceptanceTestUtils.extractDomainIdFromCreatedResourceAddress(responseSpec);
    }

    @Test
    @DisplayName("문제 정보 조회 테스트")
    void showProblem() {
        webTestClient.get()
                .uri("/problems/" + problemId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name", "test");
    }

    @Test
    @DisplayName("문제 pdf 파일 다운로드 테스트")
    void downloadFile() {
        webTestClient.get()
                .uri("/problems/" + problemId + "/files")
                .accept(MediaType.APPLICATION_PDF)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().valueMatches(HttpHeaders.CONTENT_DISPOSITION, "inline");
    }

    @Test
    @DisplayName("문제, pdf 파일 수정 테스트")
    void updateProblem() {
        ByteArrayResource file = new ByteArrayResource(new byte[]{1, 2, 3}) {
            @Override
            public String getFilename() {
                return "update.pdf";
            }
        };

        webTestClient.post()
                .uri("/problems/" + problemId)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file)
                        .with("name", "update")
                        .with("timeLimit", 3d)
                        .with("memoryLimit", 3d))
                .exchange()
                .expectStatus()
                .isNoContent();

        webTestClient.get()
                .uri("/problems/" + problemId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name", "update");
    }

    @AfterEach
    @DisplayName("문제, pdf 파일 삭제 테스트")
    void deleteProblem() {
        webTestClient.delete()
                .uri("/problems/" + problemId)
                .exchange()
                .expectStatus()
                .isNoContent();

        webTestClient.get()
                .uri("/problems/" + problemId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
