package appguarden.apppal.language.constraint.functions;

import java.util.List;

import appguarden.apppal.interfaces.ConstraintFunction;
import appguarden.apppal.language.constraint.Bool;
import appguarden.apppal.language.constraint.CE;

/**
 * Created by bogwonch on 05/03/2015.
 */
public class HasPermission implements ConstraintFunction
{
  @Override
  public int arity() { return 2; }

  @Override
  public CE eval(List<CE> args)
  {
    return new Bool(true);
  }
}
