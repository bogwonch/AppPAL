package appguarden.apppal.language.constraint;

import java.util.HashSet;
import java.util.Set;

import appguarden.apppal.language.E;

/**
 * Created by bogwonch on 02/03/2015.
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

  public Set<E> vars() { return new HashSet<>(); }
}
