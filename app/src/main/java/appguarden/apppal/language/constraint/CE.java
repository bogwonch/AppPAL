package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.Variable;
import appguarden.apppal.interfaces.EntityHolding;

/**
 * Constraint Entity.
 */
abstract public class CE implements EntityHolding, Unifiable<CE>
{
  public abstract String toString();
  public abstract Set<Variable> vars();
}
