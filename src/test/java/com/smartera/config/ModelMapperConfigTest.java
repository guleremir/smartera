package com.smartera.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelMapperConfigTest {

	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@Test
	public void testRequestMapperMatchingStrategy() {
		assertEquals(MatchingStrategies.STANDARD,
				requestMapper.getConfiguration().getMatchingStrategy());
	}

	@Test
	public void testResponseMapperMatchingStrategy() {
		assertEquals(MatchingStrategies.LOOSE,
				responseMapper.getConfiguration().getMatchingStrategy());
	}
}
