package appguarden.apppal.interfaces;

import java.util.HashMap;

import appguarden.apppal.evaluation.Substitution;
import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.language.Variable;

/**
 * Describes when there is something unifiable.
 */
public interface Unifiable<T>
{
  abstract public Unification unify(T with);
  abstract public T substitute(HashMap<Variable, Substitution> delta);
}
