package pp.block1.dfa;

import static pp.block1.dfa.State.ID6_DFA;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/** Test class for Checker implementation. */
public class GeneratorTest {
	private Generator myGen = new GeneratorImpl();// instantiate your Generator implementation

	@Test
	public void testID6() {
		dfa = ID6_DFA;
		yields("");
		yields("a12345", "a12345");
		yields("a12345AaBbCc", "a12345", "AaBbCc");
	}

	private void yields(String word, String... tokens) {
		List<String> result = myGen.scan(dfa, word);
		if (result == null) {
			Assert.fail(String.format(
                    "Word '%s' is erroneously rejected by %s", word, dfa));
		}
		Assert.assertEquals(tokens.length, result.size());
		for (int i = 0; i < tokens.length; i++) {
			Assert.assertEquals(tokens[i], result.get(i));
		}
	}

	private State dfa;
}
