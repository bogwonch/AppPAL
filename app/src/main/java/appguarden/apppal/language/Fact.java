package appguarden.apppal.language;


import android.util.Log;

import java.util.Set;

/**
 * AppPAL fact
 * E VP
 */
public class Fact
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

  public Set<E> vars()
  {
    Set<E> vars = this.subject.vars();
    vars.addAll(this.object.vars());
    return vars;
  }
}
