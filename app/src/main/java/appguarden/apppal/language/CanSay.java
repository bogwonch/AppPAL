package appguarden.apppal.language;


import java.util.Set;

/**
 * AppPAL can-say statement
 * can-say D Fact
 */
public class CanSay extends VP
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

  public Set<E> vars()
  {
    return this.fact.vars();
  }
}

