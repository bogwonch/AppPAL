package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.interfaces.Unifiable;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Constraint Function
 */
public class Function extends CE implements Unifiable<CE>
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
    final Set<Variable> vars = new HashSet<>();
    for (final CE e : this.args)
      vars.addAll(e.vars());
    return vars;
  }
  public Set<Constant> consts()
  {
    final Set<Constant> consts = new HashSet<>();
    for (final CE e : this.args)
      consts.addAll(e.consts());
    return consts;
  }

  @Override
  public Unification unify(final CE with)
  {
    if (!(with instanceof Function)) return new Unification(false);
    Function that = (Function) with;
    if (! this.name.equals(that.name)) return new Unification(false);
    int n = this.args.size();
    if (that.args.size() != n) return new Unification(false);
    final Unification unification = new Unification();
    for (int k = 0; k < n; k++)
    {
      CE thetaX = this.args.get(k).substitute(unification.theta);
      CE thetaY = that.args.get(k).substitute(unification.theta);
      unification.compose(thetaX.unify(thetaY));
      if (unification.hasFailed()) return unification;
    }
    return unification;
  }

  @Override
  public Function substitute(Map<Variable, Substitution> delta)
  {
    final List<CE> args = new LinkedList<>();
    for (final CE arg : this.args)
      args.add(arg.substitute(delta));
    return new Function(this.name, args);
  }
}
