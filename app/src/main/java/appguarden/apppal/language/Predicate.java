package appguarden.apppal.language;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AppPAL predicate
 * predicate (E+)
 */
public class Predicate extends VP
{
  public final String name;
  public final List<E> args;


  public Predicate(String name)
  {
    this(name, null);
  }

  public Predicate(String name, List<E> args)
  {
    this.name = name;
    this.args = args;
  }

  /** Check whether this predicate has arguments
   * @return boolean
   */
  private boolean hasArgs()
  {
    return !(this.args == null || this.args.isEmpty());
  }

  public String toString()
  {
    // If the predicate list is empty don't display it
    if (! this.hasArgs())
      return this.name;

    // Else this.args.size() >= 1
    String answer = this.name + "(";
    for (int i = 0; i < this.args.size() - 1; i++)
      answer += this.args.get(i) + ", ";
    answer += this.args.get(this.args.size() - 1) + ")";

    return answer;
  }

  public Set<E> vars()
  {
    Set<E> vars = new HashSet<>();
    if (this.hasArgs())
      for(E arg : this.args)
        vars.addAll(arg.vars());
    return vars;
  }
}
