package pp.block1.antlr;

import org.junit.Test;
import pp.block1.gen.LaLaLaLi;

public class LaLaLaLiTest {
    private static LexerTester tester = new LexerTester(LaLaLaLi.class);

    @Test
    public void test() {
        tester.yields("LaLaLaLiLaLaLaLiLaLaLaLi", LaLaLaLi.LALALALI, LaLaLaLi.LALALALI, LaLaLaLi.LALALALI);
        tester.yields("LaLa", LaLaLaLi.LALA);
        tester.yields("LaLaLa", LaLaLaLi.LALA, LaLaLaLi.LA);
        tester.wrong("LaLaLaLaLi");
        tester.wrong("La La La La Li");
        tester.yields("La La Laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   La La Li         ", LaLaLaLi.LALA, LaLaLaLi.LALALALI);
        tester.correct("La");
        tester.correct("LaLa");
        tester.correct("LaLaLaLi");
    }
}
