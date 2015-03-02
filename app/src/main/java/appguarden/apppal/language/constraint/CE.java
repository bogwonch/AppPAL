package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Constraint Entity.
 */
abstract public class CE
{
  public abstract String toString();
  public abstract Set<Variable> vars();
}
