package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.E;

/**
 * Constraint Entity.
 */
abstract public class CE
{
  public abstract String toString();
  public abstract Set<E> vars();
}
