package pp.block2.cc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;
import pp.block2.cc.ll.*;

public class LLCalcTest {
	/** Tests the LL-calculator for the Sentence grammar. */
	@Test
	public void testSentence() {
		Grammar g = Grammars.makeSentence();
		// Without the last (recursive) rule, the grammar is LL-1
		assertTrue(createCalc(g).isLL1());
		NonTerm subj = g.getNonterminal("Subject");
		NonTerm obj = g.getNonterminal("Object");
		NonTerm sent = g.getNonterminal("Sentence");
		NonTerm mod = g.getNonterminal("Modifier");
		g.addRule(mod, mod, mod);
		LLCalc calc = createCalc(g);
		// FIRST sets
		Term adj = g.getTerminal(Sentence.ADJECTIVE);
		Term noun = g.getTerminal(Sentence.NOUN);
		Term verb = g.getTerminal(Sentence.VERB);
		Term end = g.getTerminal(Sentence.ENDMARK);
		assertEquals(set(adj, noun), calc.getFirst().get(sent));
		assertEquals(set(adj, noun), calc.getFirst().get(subj));
		assertEquals(set(adj, noun), calc.getFirst().get(obj));
		assertEquals(set(adj), calc.getFirst().get(mod));
		// FOLLOW sets
		assertEquals(set(Symbol.EOF), calc.getFollow().get(sent));
		assertEquals(set(verb), calc.getFollow().get(subj));
		assertEquals(set(end), calc.getFollow().get(obj));
		assertEquals(set(noun, adj), calc.getFollow().get(mod));
		// is-LL1-test
		assertFalse(calc.isLL1());
	}

    @Test
    public void testIf() {
        Grammar g = Grammars.makeIf();
        LLCalc calc = createCalc(g);

        NonTerm stat = g.getNonterminal("Stat");
        NonTerm elsePart = g.getNonterminal("ElsePart");

        Term assign = g.getTerminal(If.ASSIGN);
        Term fiff = g.getTerminal(If.IF);
        Term cond = g.getTerminal(If.COND);
        Term lelsel = g.getTerminal(If.ELSE);
        Term then = g.getTerminal(If.THEN);

        // Test values of first
        for(Term term : new Term[] { assign, fiff, cond, lelsel, then }) {
            assertEquals(set(term), calc.getFirst().get(term));
        }

        assertEquals(set(fiff, assign), calc.getFirst().get(stat));
        assertEquals(set(lelsel, Symbol.EMPTY), calc.getFirst().get(elsePart));

        // Test values of follow
        assertEquals(set(lelsel, Symbol.EOF), calc.getFollow().get(stat));
        assertEquals(set(lelsel, Symbol.EOF), calc.getFollow().get(elsePart));

        assertFalse(calc.isLL1());
    }

    @Test
    public void testL() {
        Grammar g = Grammars.makeL();
        LLCalc calc = createCalc(g);

        NonTerm l = g.getNonterminal("L");
        NonTerm r = g.getNonterminal("R");
        NonTerm s = g.getNonterminal("S");
        NonTerm q = g.getNonterminal("Q");
        NonTerm t = g.getNonterminal("T");

        Term a = g.getTerminal(L.A);
        Term b = g.getTerminal(L.B);
        Term c = g.getTerminal(L.C);

        // Test values of first
        for(Term term : new Term[] { a, b, c }) {
            assertEquals(set(term), calc.getFirst().get(term));
        }

        assertEquals(set(a, b, c), calc.getFirst().get(l));
        assertEquals(set(a, c), calc.getFirst().get(r));
        assertEquals(set(b, Symbol.EMPTY), calc.getFirst().get(s));
        assertEquals(set(b), calc.getFirst().get(q));
        assertEquals(set(b, c), calc.getFirst().get(t));

        // Test values of follow
        assertEquals(set(Symbol.EOF), calc.getFollow().get(l));
        assertEquals(set(a), calc.getFollow().get(r));
        assertEquals(set(a), calc.getFollow().get(s));
        assertEquals(set(b), calc.getFollow().get(q));
        assertEquals(set(b), calc.getFollow().get(t));

        // Test values of First+
        assertEquals(set(a, c), calc.getFirstp().get(g.getRules(l).get(0)));
        assertEquals(set(b), calc.getFirstp().get(g.getRules(l).get(1)));
        assertEquals(set(a), calc.getFirstp().get(g.getRules(r).get(0)));
        assertEquals(set(c), calc.getFirstp().get(g.getRules(r).get(1)));
        assertEquals(set(b), calc.getFirstp().get(g.getRules(s).get(0)));
        assertEquals(set(Symbol.EMPTY, a), calc.getFirstp().get(g.getRules(s).get(1)));
        assertEquals(set(b), calc.getFirstp().get(g.getRules(q).get(0)));
        assertEquals(set(b), calc.getFirstp().get(g.getRules(t).get(0)));
        assertEquals(set(c), calc.getFirstp().get(g.getRules(t).get(1)));

        assertTrue(calc.isLL1());
    }

	/** Creates an LL1-calculator for a given grammar. */
	private LLCalc createCalc(Grammar g) {
		return new LLCalcImpl(g); // your implementation of LLCalc
	}

	@SuppressWarnings("unchecked")
	private <T> Set<T> set(T... elements) {
		return new HashSet<>(Arrays.asList(elements));
	}
}
