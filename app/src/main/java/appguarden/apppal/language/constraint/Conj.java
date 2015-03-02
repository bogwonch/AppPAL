package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Created by bogwonch on 02/03/2015.
 */
public class Conj extends Constraint
{
  public final Constraint lhs;
  public final Constraint rhs;

  public Conj(Constraint lhs, Constraint rhs)
  {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public String toString() { return this.lhs +", "+this.rhs; }
  public Set<Variable> vars()
  {
    Set<Variable> vars = this.lhs.vars();
    vars.addAll(this.rhs.vars());
    return vars;
  }
}
