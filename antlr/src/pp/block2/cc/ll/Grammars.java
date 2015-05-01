/**
 * 
 */
package pp.block2.cc.ll;

import pp.block2.cc.Symbol;
import pp.block2.cc.SymbolFactory;
import pp.block2.cc.NonTerm;
import pp.block2.cc.Term;

/**
 * Class containing some example grammars.
 * @author Arend Rensink
 *
 */
public class Grammars {
	/** Returns a grammar for simple English sentences. */
	public static Grammar makeSentence() {
		// Define the non-terminals
		NonTerm sent = new NonTerm("Sentence");
		NonTerm subj = new NonTerm("Subject");
		NonTerm obj = new NonTerm("Object");
		NonTerm mod = new NonTerm("Modifier");
		// Define the terminals, using the Sentence.g4 lexer grammar
		SymbolFactory fact = new SymbolFactory(Sentence.class);
		Term noun = fact.getTerminal(Sentence.NOUN);
		Term verb = fact.getTerminal(Sentence.VERB);
		Term adj = fact.getTerminal(Sentence.ADJECTIVE);
		Term end = fact.getTerminal(Sentence.ENDMARK);
		// Build the context free grammar
		Grammar g = new Grammar(sent);
		g.addRule(sent, subj, verb, obj, end);
		g.addRule(subj, noun);
		g.addRule(subj, mod, subj);
		g.addRule(obj, noun);
		g.addRule(obj, mod, obj);
		g.addRule(mod, adj);
		return g;
	}

    public static Grammar makeIf() {
        NonTerm stat = new NonTerm("Stat");
        NonTerm elsePart = new NonTerm("ElsePart");

        SymbolFactory fact = new SymbolFactory(If.class);
        Term assign = fact.getTerminal(If.ASSIGN);
        Term fiff = fact.getTerminal(If.IF);
        Term cond = fact.getTerminal(If.COND);
        Term lelsel = fact.getTerminal(If.ELSE);
        Term then = fact.getTerminal(If.THEN);

        Grammar g = new Grammar(stat);
        g.addRule(stat, assign);
        g.addRule(stat, fiff, cond, then, stat, elsePart);
        g.addRule(elsePart, lelsel, stat);
        g.addRule(elsePart, Symbol.EMPTY);

        return g;
    }

    public static Grammar makeL() {
        NonTerm l = new NonTerm("L"),
                r = new NonTerm("R"),
                s = new NonTerm("S"),
                q = new NonTerm("Q"),
                t = new NonTerm("T");

        SymbolFactory fact = new SymbolFactory(L.class);
        Term a = fact.getTerminal(L.A);
        Term b = fact.getTerminal(L.B);
        Term c = fact.getTerminal(L.C);

        Grammar g = new Grammar(l);
        g.addRule(l, r, a);
        g.addRule(l, q, b, a);
        g.addRule(r, a, b, a, s);
        g.addRule(r, c, a, b, a, s);
        g.addRule(s, b, c, s);
        g.addRule(s, Symbol.EMPTY);
        g.addRule(q, b, t);
        g.addRule(t, b, c);
        g.addRule(t, c);

        return g;
    }
}
