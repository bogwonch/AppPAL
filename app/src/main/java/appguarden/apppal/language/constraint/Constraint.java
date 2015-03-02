package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.Set;

import appguarden.apppal.language.E;

/**
 * SecPAL Constraint
 */
abstract public class Constraint
{
  public boolean isTriviallyTrue()
  {
    return false;
  }

  public abstract Set<E> vars();
  public abstract String toString();
}
