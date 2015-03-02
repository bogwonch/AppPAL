package appguarden.apppal.language;

import java.util.Set;

/**
 * SecPAL language verb phrases
 */
public abstract class VP
{
  public abstract String toString();
  public abstract Set<Variable> vars();
}
