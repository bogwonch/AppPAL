package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.Variable;
import appguarden.apppal.interfaces.EntityHolding;

/**
 * Constraint Entity.
 */
abstract public class CE implements EntityHolding
{
  public abstract String toString();
  public abstract Set<Variable> vars();
}
