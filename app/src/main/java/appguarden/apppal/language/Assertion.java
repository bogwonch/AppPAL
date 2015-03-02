package appguarden.apppal.language;

import android.util.Log;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Set;

import appguarden.apppal.grammar.AppPALEmitter;
import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;

/**
 * SecPAL Assertion
 */
public class Assertion
{
  public final E speaker;
  public final Claim says;

  public Assertion(E speaker, Claim says)
  {
    this.speaker = speaker;
    this.says = says;
  }

  /** Create an assertion by parsing a string
   * @param str the assertion to parse
   * @returns the parsed assertion
   */
  public static Assertion parse(String str) throws IOException
  {
    InputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.assertion();
    AppPALEmitter emitter = new AppPALEmitter();
    return (Assertion) emitter.visit(tree);
  }

  public String toString()
  {
    return this.speaker + " says " + this.says + ".";
  }

  /** @brief Check whether the assertion meets the SecPAL safety conditions.
   *
   * An assertion a (A says f if f1,...,fn where c) is safe if:
   *
   * 1. a) if f is flat => all v in vars(f) are safe in a.
   *    b) if f is (e can-say ...) => e is safe in a.
   * 2. vars c are a subset of the vars of the consequent and antecedent.
   * 3. all antecedent facts are flat.
   *
   * @returns boolean
   */
  public boolean isSafe()
  {
    // 1. a) if f is flat => all v in vars(f) are safe in a.
    if (this.says.consequent.isFlat())
    {
      for (E e : this.says.consequent.vars())
        if (! e.safeIn(this)) { return false; }
    }
    //   b) if f is (e can-say ...) => e is safe in a.
    else
    {
      assert(this.says.consequent.object instanceof CanSay);
      if (! this.says.consequent.subject.safeIn(this))
        return false;
    }

    // 2. vars c are a subset of the vars of the consequent and antecedent.
    Set<E> c_vars = this.says.constraint.vars();
    Set<E> o_vars = this.says.consequent.vars();
    o_vars.addAll(this.says.antecedentVars());
    if (! o_vars.containsAll(c_vars))
      return false;

    // 3. all antecedent facts are flat.
    if (this.says.hasAntecedents())
      for (Fact f : this.says.antecedents)
        if (! f.isFlat()) { return false; }

    return true;
  }
}
