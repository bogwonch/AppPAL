package appguarden.apppal.language;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.grammar.AppPALEmitter;
import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;
import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.constraint.CE;
import appguarden.apppal.interfaces.EntityHolding;

/**
 * AppPAL entity
 */
public abstract class E extends CE implements EntityHolding, Unifiable<CE>
{
  public final String name;
  public final EKind kind;
  protected int scope; // Unscoped

  public E(String name, EKind kind)
  {
    this.name = name;
    this.kind = kind;
    this.scope = 0; // No scope by default
  }

  public abstract String toString();

  public Set<Variable> vars()
  {
    Set<Variable> ans = new HashSet<>();
    if (this.kind == EKind.VARIABLE)
      ans.add((Variable) this);
    return ans;
  }

  public Set<Constant> consts()
  {
    Set<Constant> ans = new HashSet<>();
    if (this.kind == EKind.CONSTANT)
      ans.add((Constant) this);
    return ans;
  }

  /*
   * Java is an *incredibly* stupid language and can't cope with ideas like equality well.
   * Oh for C... Oh for Haskell...
   */
  @Override
  public int hashCode()
  {
    int result = 17;
    result = result * 31 + this.name.hashCode();
    result = result * 31 + this.kind.hashCode();
    result = result * 31 + new Integer(this.scope).hashCode();
    return result;
  }

  @Override
  public boolean equals(Object other)
  {
    if (!(other instanceof E))
    {
      return false;
    }
    E e = (E) other;
    if (this.kind != e.kind) return false;
    if (!this.name.equals(e.name)) return false;
    if (this.scope != e.scope) return false;
    return true;
  }


  /**
   * @param a assertion to check safety in
   * @brief Check whether an entity is safe in an assertion
   *
   * A variable v is safe in an Assertion of the form:
   * A says f if f_1, ..., f_n where c
   * if v is in f and v is in f_1,...f_n.
   * @returns boolean
   */
  public boolean safeIn(Assertion a)
  {
    if (this.kind == EKind.CONSTANT) return true;
    else return (a.says.consequent.vars().contains(this) && a.says.antecedentVars().contains(this));
  }

  /**
   * Create a Constant by parsing a string
   *
   * @param str the Constant to parse
   * @returns the parsed Constant
   */
  public static E parse(String str) throws IOException
  {
    InputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.e();
    AppPALEmitter emitter = new AppPALEmitter();
    return (E) emitter.visit(tree);
  }

  public Unification unify(E other)
  {
    Unification unification = new Unification();
    if (this.kind == EKind.CONSTANT && other.kind == EKind.CONSTANT)
    {
      if (!this.name.equals(other.name))
        unification.fails();
    }
    else
    {
      Variable x;
      E t;
      if (this.kind == EKind.VARIABLE)
      {
        x = (Variable) this;
        t = other;
      }
      else
      {
        x = (Variable) other;
        t = this;
      }

      if (!x.equals(t))
        try
        {
          unification.add(x, t);
        } catch (Exception e)
        {
          unification.fails();
        }
    }
    return unification;
  }

  public Unification unify(CE other)
  {
    if (other instanceof E) return this.unify((E) other);
    else return new Unification(false);
  }

  public E substitute(Map<Variable, Substitution> delta)
  {
    if (this.kind == EKind.CONSTANT) return this;
    if (delta.containsKey(this))
    {
      Substitution theta = delta.get(this);
      return theta.to;
    }
    return this;
  }

  @Override
  public void scope(int scope) { this.scope = scope; }
}
