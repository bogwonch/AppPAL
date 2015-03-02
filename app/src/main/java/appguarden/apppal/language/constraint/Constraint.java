package appguarden.apppal.language.constraint;

import appguarden.apppal.interfaces.EntityHolding;

/**
 * SecPAL Constraint
 */
abstract public class Constraint implements EntityHolding
{
  public boolean isTriviallyTrue()
  {
    return false;
  }
  public abstract String toString();
}
