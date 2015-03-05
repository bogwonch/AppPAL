package apppal.logic.language.constraint.functions;

import java.util.List;

import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.language.constraint.Bool;
import apppal.logic.language.constraint.CE;

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
