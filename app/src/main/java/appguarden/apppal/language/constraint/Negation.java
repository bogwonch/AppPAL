package appguarden.apppal.language.constraint;

import java.util.Set;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Negation constraint
 */
public class Negation extends Constraint
{
  final public Constraint value;

  public Negation(Constraint c) { this.value = c; }
  public String toString() { return "! "+this.value; }
  public Set<Variable> vars() { return this.value.vars(); }
}
