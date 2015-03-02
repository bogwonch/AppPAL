package appguarden.apppal.language;


import java.util.Set;

import appguarden.apppal.interfaces.EntityHolding;

/**
 * AppPAL can-say statement
 * can-say D Fact
 */
public class CanSay extends VP implements EntityHolding
{
  public final D d;
  public final Fact fact;

  public CanSay(D d, Fact fact)
  {
    this.d = d;
    this.fact = fact;
  }

  public String toString()
  {
    return "can-say " + this.d + " " + this.fact;
  }

  public Set<Variable> vars() { return this.fact.vars(); }
  public Set<Constant> consts() { return this.fact.consts(); }
}

