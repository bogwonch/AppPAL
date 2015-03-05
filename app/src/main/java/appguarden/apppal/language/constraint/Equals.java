package appguarden.apppal.language.constraint;

import java.util.Map;
import java.util.Set;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.Variable;

/**
 * Equality in constraints.
 */
public class Equals extends Constraint implements Unifiable<Constraint>
{
  public final CE lhs;
  public final CE rhs;

  public Equals(CE lhs, CE rhs)
  {
    super();
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
  public Set<Constant> consts()
  {
    Set<Constant> consts = this.lhs.consts();
    consts.addAll(this.rhs.consts());
    return consts;
  }

  @Override
  public Unification unify(Constraint with)
  {
    if (! (with instanceof Equals)) return new Unification(false);
    final Equals that = (Equals) with;
    final Unification unification = this.lhs.unify(that.lhs);
    if (unification.hasFailed()) return unification;
    final CE thetaX = this.rhs.substitute(unification.theta);
    final CE thetaY = that.rhs.substitute(unification.theta);
    unification.compose(thetaX.unify(thetaY));
    return unification;
  }

  @Override
  public Constraint substitute(Map<Variable, Substitution> delta)
  { return new Equals(this.lhs.substitute(delta), this.rhs.substitute(delta)); }

  @Override
  public boolean isTrue()
  {
    return lhs.eval().equals(rhs.eval());
  }

  @Override
  public void scope(int scope)
  {
    this.lhs.scope(scope);
    this.rhs.scope(scope);
  }
}
