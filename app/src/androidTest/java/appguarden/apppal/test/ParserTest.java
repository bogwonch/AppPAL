package appguarden.apppal.test;

import android.test.InstrumentationTestCase;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;

/**
 * Created by bogwonch on 01/03/2015.
 */
public class ParserTest extends InstrumentationTestCase {
    public void testParser() throws Exception
    {
        InputStream in = new StringBufferInputStream("\"Alice\" says Bob isCool.");
        ANTLRInputStream input = new ANTLRInputStream(in);
        AppPALLexer lexer = new AppPALLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AppPALParser parser = new AppPALParser(tokens);

        ParseTree tree = parser.assertion();
        assertEquals(tree.toStringTree(parser),
          "(assertion (e \"Alice\") says (claim (fact (e Bob) (vp (predicate isCool)))) .))]"
        );
    }
}
