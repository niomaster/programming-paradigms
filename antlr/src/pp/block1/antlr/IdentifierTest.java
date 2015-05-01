package pp.block1.antlr;

import org.junit.Test;
import pp.block1.gen.Identifier;

public class IdentifierTest {
    private static LexerTester tester = new LexerTester(Identifier.class);

    @Test
    public void testAlles() {
        tester.yields("abcdefghijkl", Identifier.IDENTIFIER, Identifier.IDENTIFIER);
        tester.yields("");
        tester.yields("abc123def456", Identifier.IDENTIFIER, Identifier.IDENTIFIER);
        tester.wrong("123456");
        tester.yields("a12345", Identifier.IDENTIFIER);
        tester.wrong("12345");
        tester.wrong("abcde");
        tester.wrong("abcdefabcde");
        tester.wrong("abcdef1bcdef");
    }
}
