package appguarden.apppal.interfaces;

import java.util.Set;

import appguarden.apppal.language.Constant;
import appguarden.apppal.language.Variable;

/**
 * Interface for classes holding Es so we can pull them out at will.
 */
public interface EntityHolding
{
  Set<Variable> vars();
  Set<Constant> consts();
}
