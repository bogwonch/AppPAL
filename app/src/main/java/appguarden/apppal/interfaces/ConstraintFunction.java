package appguarden.apppal.interfaces;

import java.util.List;

import appguarden.apppal.language.constraint.CE;

/**
 * Created by bogwonch on 05/03/2015.
 */
public interface ConstraintFunction
{
  public abstract int arity();
  public abstract CE eval(List<CE> args);
}
