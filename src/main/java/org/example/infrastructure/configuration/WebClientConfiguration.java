//package org.example.infrastructure.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.codec.json.Jackson2JsonDecoder;
//import org.springframework.http.codec.json.Jackson2JsonEncoder;
//import org.springframework.web.reactive.function.client.ExchangeStrategies;
//import org.springframework.web.reactive.function.client.WebClient;
//
//
//@Configuration
//public class WebClientConfiguration {
//
//
//    public static final int TIMEOUT = 5000;
//
//    @Value("${api.restApiToConsume.url}")
//    private String apiUrl;
//
//    @Bean
//    public ApiClient petStoreApiClient(final ObjectMapper objectMapper) {
//
//        final var exchangeStrategies = ExchangeStrategies
//                .builder()
//                .codecs(configurer -> {
//                    configurer
//                            .defaultCodecs()
//                            .jackson2JsonEncoder(
//                                    new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)
//                            );
//                    configurer
//                            .defaultCodecs()
//                            .jackson2JsonDecoder(
//                                    new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON)
//                            );
//                })
//                .build();
//
//        final var webClient = WebClient.builder()
//                .exchangeStrategies(exchangeStrategies)
//                .build();
//
//        ApiClient apiClient = new ApiClient(webClient);
//        apiClient.setBasePath(apiUrl);
//        return apiClient;
//    }
//
//    @Bean
//    public PetApi petApi(final ObjectMapper objectMapper) {
//        return new PetApi(petStoreApiClient(objectMapper));
//    }
//}
