package pp.block1.antlr;

import org.junit.Test;
import pp.block1.gen.PL1String;

public class PL1StringTest {
    private static LexerTester tester = new LexerTester(PL1String.class);

    @Test
    public void testAlles() {
        tester.yields("\"This is a quote: \"\", \"\"some more\"\" text\"\"\"", PL1String.STRING);
        tester.yields("\"\"\"\"\"\"", PL1String.STRING);
        tester.yields("\"a\"\"a\"\"a\"", PL1String.STRING, PL1String.STRING, PL1String.STRING);
        tester.wrong("\"\"\"");
    }
}
