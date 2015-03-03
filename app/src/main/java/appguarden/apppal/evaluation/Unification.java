package appguarden.apppal.evaluation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Holds the unification of two assertions
 */
public class Unification
{
  private Map<Variable, Substitution> theta;

  private boolean success;

  public Unification()
  {
    this.theta = new HashMap<>();
    this.success = true;
  }

  public Unification(boolean success)
  {
    this();
    if (!success) this.fails();
  }

  public void add(Variable from, E to) throws Exception
  {
    if (this.hasFailed()) throw new Exception("cannot add to failed unification");
    if (this.theta.containsKey(from))
      throw new IllegalArgumentException("attempt to unify " + from + " to multiple values");
    Substitution delta = new Substitution(from, to);
    this.theta.put(from, delta);
  }

  public void fails()
  {
    this.theta = null;
    this.success = false;
  }

  public boolean hasFailed()
  {
    return this.theta == null || this.success == false;
  }

  public String toString()
  {
    if (!this.success)
    {
      return "_|_";
    }

    Object[] deltas = this.theta.values().toArray();
    if (deltas.length == 0) return "{}";

    String str = "{";
    for (int i = 0; i < deltas.length; i++)
      str += ((i == 0)? " ":", ") + deltas[i];
    str += " }";
    return str;
  }
}
