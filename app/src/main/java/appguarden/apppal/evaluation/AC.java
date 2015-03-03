package appguarden.apppal.evaluation;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import appguarden.apppal.grammar.AppPALEmitter;
import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;
import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.Constant;

/**
 * An assertion context is the result of loading a policy file into AppPAL.
 * It contains all manner of information about how AppPAL statements should be evaluated.
 */
public class AC
{
  private List<Assertion> assertions;
  private Set<Constant> constants;

  /**
   * Check all assertions in the context meet the safety property.
   * @throws IllegalArgumentException
   */
  private void checkAssertionSafety() throws IllegalArgumentException
  {
    for (Assertion a : this.assertions)
      if (! a.isSafe())
        throw new IllegalArgumentException("unsafe assertion: "+a);
  }

  /**
   * Populate the list of constants from the assertions.
   */
  private void populateConstants()
  {
    this.constants = new HashSet<>();
    for (Assertion a : this.assertions)
      this.constants.addAll(a.consts());
  }

  /**
   * Parse an Assertion context out of an input stream
   * @param in input stream to parse
   * @return the list of assertions
   * @throws IOException
   */
  public static List<Assertion> parse(InputStream in) throws IOException
  {
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.ac();
    AppPALEmitter emitter = new AppPALEmitter();

    return (List<Assertion>) emitter.visit(tree);
  }

  public AC(String str) throws IOException
  { this(new ByteArrayInputStream(str.getBytes("UTF-8"))); }

  public AC(InputStream in) throws IOException { this(AC.parse(in)); }

  public AC(List<Assertion> as)
  {
    this.assertions = as;
    this.checkAssertionSafety();
    this.populateConstants();
  }

  public List<Assertion> getAssertions()
  {
    return this.assertions;
  }
}
