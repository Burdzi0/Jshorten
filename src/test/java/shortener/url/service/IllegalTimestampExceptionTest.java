package shortener.url.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalTimestampExceptionTest {

	@Test
	void test() {
		assertThrows(IllegalTimestampException.class, () -> {
			throw new IllegalTimestampException();
		});
	}

}