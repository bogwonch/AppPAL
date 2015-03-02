package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.Set;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Variable;

/**
 * Booleans are the only value we need...
 */
public class Bool extends CE
{
  public final boolean value;
  public Bool(boolean value)
  {
    this.value = value;
  }

  public String toString()
  {
    if (this.value)
      return "true";
    else
      return "false";
  }

  public Set<Variable> vars() { return new HashSet<>(); }
}
