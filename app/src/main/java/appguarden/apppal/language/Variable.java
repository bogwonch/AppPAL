package appguarden.apppal.language;

import java.io.IOException;

import appguarden.apppal.evaluation.Unification;

/**
 * A variable instance of an entity
 */
public class Variable extends E
{

  public Variable(String name)
  {
    super(name, EKind.VARIABLE);
  }

  public String toString()
  {
    // For test purposes if something isn't part of an assertion don't show it.
    if (this.scope > 0)
      return this.name+"."+this.scope;
    else
      return this.name;
  }

  public static Variable parse(String str) throws IOException
  {
    E e = E.parse(str);
    if (e instanceof Variable)
      return (Variable) e;
    else
      throw new IOException("parsed something else when parsing a variable");
  }
}
