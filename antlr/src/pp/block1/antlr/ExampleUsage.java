package pp.block1.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import pp.block1.gen.Example;

public class ExampleUsage {
	public static void main(String[] args) {
		scan("while do");
		scan("while while do");
		scan("whiledo done");
	}

	public static void scan(String text) {
		CharStream stream = new ANTLRInputStream(text);
		Lexer lexer = new Example(stream);
		for (Token token : lexer.getAllTokens()) {
			System.out.print(token.toString() + " ");
		}
		System.out.println();
	}
}
