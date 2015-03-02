package appguarden.apppal.language;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import appguarden.apppal.language.constraint.Constraint;
import appguarden.apppal.language.constraint.Sat;

/**
 * AppPAL claims
 * fact [ if fact,* ] [ where c ]
 */
public class Claim
{
  public final Fact consequent;
  public final List<Fact> antecedents;
  public final Constraint constraint;

  /**
   * Creates a new claim
   */
  public Claim(Fact f, List<Fact> a, Constraint c)
  {
    this.consequent = f;

    if (a == null)
      this.antecedents = new LinkedList<>();
    else
      this.antecedents = a;

    if (c == null)
      this.constraint = new Sat();
    else
      this.constraint = c;
  }

  /* Utility constructors */
  public Claim(Fact c)
  {
    this(c, null, new Sat());
  }

  public Claim(Fact f, List<Fact> a)
  {
    this(f, a, new Sat());
  }

  public Claim(Fact consequent, Constraint c)
  {
    this(consequent, null, c);
  }

  public String toString()
  {
    String answer = this.consequent.toString();

    if (this.hasAntecedents())
    {
      answer += " if ";
      for (int i = 0; i < this.antecedents.size() - 1; i++)
        answer += this.antecedents.get(i) + ", ";
      answer += this.antecedents.get(this.antecedents.size() - 1);
    }

    if (!this.constraint.isTriviallyTrue())
      answer += " where " + this.constraint;

    return answer;
  }

  public boolean hasAntecedents()
  {
    return ! (this.antecedents == null || this.antecedents.isEmpty());
  }

  public Set<E> vars()
  {
    Set<E> vars = this.consequent.vars();
    vars.addAll(this.antecedentVars());
    vars.addAll(this.constraint.vars());
    return vars;
  }

  /**
   * Get the vars from the antecedent conditions
   * @return Set of variables in the antecedent conditions
   */
  public Set<E> antecedentVars()
  {
    Set<E> vars = new HashSet<>();
    if (this.hasAntecedents())
      for (Fact f : this.antecedents)
        vars.addAll(f.vars());
    return vars;
  }


}
