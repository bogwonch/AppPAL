package appguarden.apppal.evaluation;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * A substitution
 */
public class Substitution
{
  public final Variable from;
  public final E to;

  public Substitution(Variable from, E to)
  {
    this.from = from;
    this.to = to;
  }

  public String toString() { return this.from+"=>"+this.to; }
}
