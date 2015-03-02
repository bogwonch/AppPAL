package appguarden.apppal.language;

import java.util.Set;

import appguarden.apppal.interfaces.EntityHolding;

/**
 * SecPAL language verb phrases
 */
public abstract class VP implements EntityHolding
{
  public abstract String toString();
  public abstract Set<Variable> vars();
}
