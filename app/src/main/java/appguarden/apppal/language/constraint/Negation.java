package appguarden.apppal.language.constraint;

import java.util.Map;
import java.util.Set;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.Variable;

/**
 * Negation constraint
 */
public class Negation extends Constraint implements Unifiable<Constraint>
{
  final public Constraint value;

  public Negation(Constraint c) {
    super();
    this.value = c;
  }
  public String toString() { return "! "+this.value; }
  public Set<Variable> vars() { return this.value.vars(); }
  public Set<Constant> consts() { return this.value.consts(); }

  @Override
  public Unification unify(final Constraint with)
  { if (with instanceof Negation)
      return this.value.unify(((Negation) with).value);
    else return new Unification(false);
  }

  @Override
  public Constraint substitute(final Map<Variable, Substitution> delta)
  { return new Negation(this.value.substitute(delta)); }

  @Override
  public void scope(int scope)
  { this.value.scope(scope); }
}
