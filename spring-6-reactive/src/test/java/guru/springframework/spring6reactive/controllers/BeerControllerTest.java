package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListBeers() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void testGetById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(3)
    void testCreateBeer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    @Order(4)
    void testUpdateBeer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteBeer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @Order(1000)
    void testCreateBeerBadData() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(BeerController.BEER_PATH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(1001)
    void testGetByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(BeerController.BEER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1002)
    void testUpdateBeerBadRequest() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerStyle("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(1003)
    void testUpdateBeerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1004)
    void testPatchIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(BeerController.BEER_PATH_ID, 999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1005)
    void testDeleteNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }
}
