package com.niulbird.clubmgr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.task.FixtureAvailabilityJob;

public class FixtureAvailabilityJobTest extends BaseTestCase {
	@Autowired
	FixtureAvailabilityJob fixtureAvailabilityJob;
	
	@BeforeEach
	public void setup() {
	}
	
	@Test
	public void testFixtureAvailabilityJob() {
		Assertions.assertNotNull(fixtureAvailabilityJob);
	}
}
