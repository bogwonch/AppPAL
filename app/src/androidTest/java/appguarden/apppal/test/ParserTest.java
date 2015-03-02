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
  public void testParseConstant() throws Exception
  {
    String str = "\"Alice\"";
    InputStream in = new StringBufferInputStream(str);
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.e();
    AppPALEmitter emitter = new AppPALEmitter();
    E a = (appguarden.apppal.language.E) emitter.visit(tree);
    assertEquals(a.toString(), str);
  }

  public void testParseVariable() throws Exception
  {
    String str = "Bob";
    InputStream in = new StringBufferInputStream(str);
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.e();
    AppPALEmitter emitter = new AppPALEmitter();
    E a = (appguarden.apppal.language.E) emitter.visit(tree);
    assertEquals(a.toString(), str);
  }

  public void testParseAssertionSimple() throws Exception
  {
    String str = "\"Alice\" says Bob isCool.";
    InputStream in = new StringBufferInputStream(str);
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.assertion();
    AppPALEmitter emitter = new AppPALEmitter();
    Assertion a = (appguarden.apppal.language.Assertion) emitter.visit(tree);
    //Log.d("antlr:assertion", a.toString());
    //Log.d("antlr:input    ", str);
    assertEquals(a.toString(), str);
  }

  public void testParseAssertionArgsAntecedent() throws Exception
  {
    String str = "\"Alice\" says Bob can-say 0 \"Alice\" likesJazz if Bob likes(\"Alice\", \"Mallory\").";
    InputStream in = new StringBufferInputStream(str);
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.assertion();
    AppPALEmitter emitter = new AppPALEmitter();
    Assertion a = (appguarden.apppal.language.Assertion) emitter.visit(tree);
    Log.d("antlr:assertion", a.toString());
    Log.d("antlr:input    ", str);
    assertEquals(a.toString(), str);
  }
}
