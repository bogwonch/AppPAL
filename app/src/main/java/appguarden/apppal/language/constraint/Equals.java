package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.E;

/**
 * Created by bogwonch on 02/03/2015.
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

  public Set<E> vars()
  {
    Set<E> vars = this.lhs.vars();
    vars.addAll(this.rhs.vars());
    return vars;
  }
}
