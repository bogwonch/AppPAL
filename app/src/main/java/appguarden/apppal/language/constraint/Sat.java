package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.Variable;

/**
 * The trivially true class
 */
public class Sat extends Constraint implements Unifiable<Constraint>
{
  public Sat() {
    super();
  }

  public String toString()
  {
    return "sat";
  }
  public Set<Variable> vars() { return new HashSet<>(); }
  public Set<Constant> consts() { return new HashSet<>(); }


  @Override
  public boolean isTriviallyTrue() { return true; }

  @Override
  public Unification unify(final Constraint with)
  { return new Unification(with instanceof Sat); }

  @Override
  public Constraint substitute(final Map<Variable, Substitution> delta)
  { return this; }
}
