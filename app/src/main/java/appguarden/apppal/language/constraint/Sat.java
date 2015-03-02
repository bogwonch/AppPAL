package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.Set;

import appguarden.apppal.language.E;

/**
 * The trivially true class
 */
public class Sat extends Constraint
{
  public Sat() {}

  public String toString()
  {
    return "sat";
  }
  public Set<E> vars() { return new HashSet<>(); }

  @Override
  public boolean isTriviallyTrue() { return true; }
}
