package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import appguarden.apppal.language.Constant;
import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

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

  public Set<Variable> vars()
  {
    Set<Variable> vars = new HashSet<>();
    for (CE e : this.args)
      vars.addAll(e.vars());
    return vars;
  }
  public Set<Constant> consts()
  {
    Set<Constant> consts = new HashSet<>();
    for (CE e : this.args)
      consts.addAll(e.consts());
    return consts;
  }
}
