package appguarden.apppal.language;

import android.util.Log;

import java.util.Set;

/**
 * SecPAL Assertion
 */
public class Assertion
{
  public final E speaker;
  public final Claim says;

  public Assertion(E speaker, Claim says)
  {
    this.speaker = speaker;
    this.says = says;
  }

  public String toString()
  {
    return this.speaker + " says " + this.says + ".";
  }

  /** @brief Check whether the assertion meets the SecPAL safety conditions.
   *
   * An assertion a (A says f if f1,...,fn where c) is safe if:
   *
   * 1. a) if f is flat => all v in vars(f) are safe in a.
   *    b) if f is (e can-say ...) => e is safe in a.
   * 2. vars c are a subset of the vars of the consequent and antecedent.
   * 3. all antecedent facts are flat.
   *
   * @returns boolean
   */
  public boolean isSafe()
  {
    // 1. a) if f is flat => all v in vars(f) are safe in a.
    if (this.says.consequent.isFlat())
    {
      for (E e : this.says.consequent.vars())
        if (! e.safeIn(this)) { return false; }
    }
    //   b) if f is (e can-say ...) => e is safe in a.
    else
    {
      assert(this.says.consequent.object instanceof CanSay);
      if (! this.says.consequent.subject.safeIn(this))
        return false;
    }

    // 2. vars c are a subset of the vars of the consequent and antecedent.
    Set<E> o_vars = this.says.consequent.vars();
    o_vars.addAll(this.says.antecedentVars());
    if (! o_vars.containsAll(this.says.constraint.vars()))
      return false;

    // 3. all antecedent facts are flat.
    if (this.says.hasAntecedents())
      for (Fact f : this.says.antecedents)
        if (! f.isFlat()) { return false; }

    return true;
  }
}
