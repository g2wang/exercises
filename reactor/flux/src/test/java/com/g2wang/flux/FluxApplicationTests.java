package com.g2wang.flux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class FluxApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testFlux() {
		Flux<String> input = Flux.just("string 1", "string 2", "string 3");
		input.subscribe(System.out::println);
		StepVerifier.create(input.log())
				.expectNext("string 1")
				.expectNext("string 2")
				.expectNext("string 3")
                .verifyComplete();
	}

}