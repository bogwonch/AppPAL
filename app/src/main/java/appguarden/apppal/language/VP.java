package appguarden.apppal.language;

import java.util.Set;

import appguarden.apppal.interfaces.EntityHolding;
import appguarden.apppal.interfaces.Unifiable;

/**
 * SecPAL language verb phrases
 */
public abstract class VP implements EntityHolding, Unifiable<VP>
{
  public abstract String toString();
  public abstract Set<Variable> vars();
  public abstract void scope(int scope);
}
