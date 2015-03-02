package appguarden.apppal.language;


import java.util.Set;

import appguarden.apppal.interfaces.EntityHolding;

/**
 * SecPAL can-act-as statement
 * can-act-as E
 */
public class CanActAs extends VP implements EntityHolding
{
  public final E renaming;

  public CanActAs(E renaming)
  {
    this.renaming = renaming;
  }

  public String toString()
  {
    return "can-act-as " + this.renaming;
  }

  public Set<Variable> vars()
  {
    return renaming.vars();
  }
  public Set<Constant> consts() { return renaming.consts(); }
}
