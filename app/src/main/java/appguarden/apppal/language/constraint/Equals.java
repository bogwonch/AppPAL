package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Equality in constraints.
 */
public class Equals extends Constraint
{
  public final CE lhs;
  public final CE rhs;

  public Equals(CE lhs, CE rhs)
  {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public String toString() { return this.lhs+" = "+this.rhs; }

  public Set<Variable> vars()
  {
    Set<Variable> vars = this.lhs.vars();
    vars.addAll(this.rhs.vars());
    return vars;
  }
}
