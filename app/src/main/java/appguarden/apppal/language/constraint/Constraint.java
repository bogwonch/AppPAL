package appguarden.apppal.language.constraint;

import java.util.Map;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.interfaces.EntityHolding;
import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.Variable;

/**
 * SecPAL Constraint
 */
abstract public class Constraint implements EntityHolding, Unifiable<Constraint>
{
  public abstract boolean isTrue();
  public boolean isTriviallyTrue()
  {
    return false;
  }
  public void scope(int scope){}
  public abstract String toString();
}
