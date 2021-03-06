package pp.block2.cc.ll;


import org.antlr.v4.runtime.Lexer;
import pp.block2.cc.AST;
import pp.block2.cc.NonTerm;
import pp.block2.cc.ParseException;
import pp.block2.cc.Parser;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;
import pp.block2.cc.test.LLCalcImpl;

import java.util.List;
import java.util.Map;

/** Generic table-driven LL(1)-parser. */
public class GenericLLParser implements Parser {
	public GenericLLParser(Grammar g) {
		this.g = g;
		this.calc = new LLCalcImpl(g); // here use your own class
	}

	private final Grammar g;
	private final LLCalc calc;

	// fill in

	private Map<NonTerm, List<Rule>> getLL1Table() {
		if (ll1Table == null) {
			ll1Table = calcLL1Table();
		}
		return ll1Table;
	}

	/** Constructs the {@link #ll1Table}. */
	private Map<NonTerm, List<Rule>> calcLL1Table() {
		// fill in
        return null;
	}

	/** Map from non-terminals to lists of rules indexed by token type. */
	private Map<NonTerm, List<Rule>> ll1Table;

    @Override
    public AST parse(Lexer lexer) throws ParseException {
        return null;
    }
}
