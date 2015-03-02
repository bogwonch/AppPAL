package appguarden.apppal.test;

import android.test.InstrumentationTestCase;
import android.util.Log;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import appguarden.apppal.grammar.AppPALEmitter;
import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;
import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.E;

/**
 * Parsing test cases
 */
public class ParserTest extends InstrumentationTestCase
{
  public void testParseAssertionSimple() throws Exception
  {
    String str = "\"Alice\" says Bob isCool.";
    assertEquals(Assertion.parse(str).toString(), str);
  }

  public void testParseAssertionArgsAntecedent() throws Exception
  {
    String str = "\"Alice\" says Bob can-say 0 \"Alice\" likesJazz if Bob likes(\"Alice\", \"Mallory\").";
    assertEquals(Assertion.parse(str).toString(), str);
  }

  public void testParseConstraint() throws Exception
  {
    String str = "X says Y isCool where equal(X, Y) = true, ! equal(Y, X) = false.";
    assertEquals(Assertion.parse(str).toString(), str);
  }
}
