package appguarden.apppal.language;


import java.util.Set;

import appguarden.apppal.interfaces.EntityHolding;

/**
 * AppPAL fact
 * E VP
 */
public class Fact implements EntityHolding
{
  public final E subject;
  public final VP object;

  public Fact(E subject, VP object)
  {
    this.subject = subject;
    this.object = object;
  }

  public String toString()
  {
    return this.subject + " " + this.object;
  }

  /**
   * Is the fact flat (i.e. not of the can-say form)
   * @returns boolean
   */
  public boolean isFlat()
  {
    return ! (this.object instanceof CanSay);
  }

  public Set<Variable> vars()
  {
    Set<Variable> vars = this.subject.vars();
    vars.addAll(this.object.vars());
    return vars;
  }

  public Set<Constant> consts()
  {
    Set<Constant> consts = this.subject.consts();
    consts.addAll(this.object.consts());
    return consts;
  }
}
