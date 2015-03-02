package appguarden.apppal.language;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import appguarden.apppal.language.constraint.CE;

/**
 * AppPAL entity
 */
public abstract class E extends CE
{
  public final String name;
  public final EKind kind;

  public E(String name, EKind kind)
  {
    this.name = name;
    this.kind = kind;
  }

  public abstract String toString();

  public Set<E> vars()
  {
    Set<E> ans = new HashSet<>();
    if (this.kind == EKind.VARIABLE)
      ans.add(this);
    return ans;
  }

  /*
   * Java is an *incredibly* stupid language and can't cope with ideas like equality well.
   * Oh for C... Oh for Haskell...
   */
  @Override
  public int hashCode()
  {
    int result = 17;
    result = result * 31 + this.name.hashCode();
    result = result * 31 + this.kind.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object other)
  {
    if (!(other instanceof E)) {
      return false;
    }
    E e = (E) other;
    if (this.kind != e.kind) return false;
    if (! this.name.equals(e.name)) return false;
    return true;
  }


  /**
   * @brief Check whether an entity is safe in an assertion
   *
   * A variable v is safe in an Assertion of the form:
   *   A says f if f_1, ..., f_n where c
   * if v is in f and v is in f_1,...f_n.
   *
   * @param a assertion to check safety in
   * @returns boolean
   */
  public boolean safeIn(Assertion a)
  {
    if (this.kind == EKind.CONSTANT) return true;
    else return (a.says.consequent.vars().contains(this) && a.says.antecedentVars().contains(this));
  }
}
