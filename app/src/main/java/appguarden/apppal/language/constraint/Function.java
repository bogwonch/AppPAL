package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import appguarden.apppal.language.E;

/**
 * Constraint Function
 */
public class Function extends CE
{
  public final String name;
  public final List<CE> args;

  public Function(String name, List<CE>args)
  {
    this.name = name;
    if (args == null)
      this.args = new LinkedList<>();
    else
      this.args = args;
  }

  public String toString()
  {
    String str = this.name+'(';
    if (! this.args.isEmpty())
    {
      str = str + this.args.get(0);
      for (int i = 1; i < this.args.size(); i++)
        str += ", "+this.args.get(i);
    }
    return str+")";
  }

  public Set<E> vars()
  {
    Set<E> vars = new HashSet<>();
    for (CE e : this.args)
      vars.addAll(e.vars());
    return vars;
  }
}
