package appguarden.apppal.language;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.grammar.AppPALEmitter;
import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;
import appguarden.apppal.interfaces.EntityHolding;

/**
 * AppPAL fact
 * E VP
 */
public class Fact implements EntityHolding
{
  public final E subject;
  public final VP object;

  public Fact(E subject, VP object)
  {
    this.subject = subject;
    this.object = object;
  }

  public String toString()
  {
    return this.subject + " " + this.object;
  }

  /**
   * Is the fact flat (i.e. not of the can-say form)
   * @returns boolean
   */
  public boolean isFlat()
  {
    return ! (this.object instanceof CanSay);
  }

  public Set<Variable> vars()
  {
    Set<Variable> vars = this.subject.vars();
    vars.addAll(this.object.vars());
    return vars;
  }

  public Set<Constant> consts()
  {
    Set<Constant> consts = this.subject.consts();
    consts.addAll(this.object.consts());
    return consts;
  }

  public Unification unify(Fact fact)
  {
    Unification tau = this.subject.unify(fact.subject);
    if (tau.hasFailed()) return tau;

    VP thisObj = this.object.substitute(tau.theta);
    VP thatObj = fact.object.substitute(tau.theta);

    tau.compose(thisObj.unify(thatObj));
    return tau;
  }

  public Fact substitute(Map<Variable, Substitution> delta)
  {
    E subject = this.subject.substitute(delta);
    VP object = this.object.substitute(delta);
    return new Fact(subject, object);
  }

  /** Create a fact by parsing a string
   * @param str the fact to parse
   * @returns the parsed fact
   */
  public static Fact parse(String str) throws IOException
  {
    InputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.fact();
    AppPALEmitter emitter = new AppPALEmitter();
    return (Fact) emitter.visit(tree);
  }
}
