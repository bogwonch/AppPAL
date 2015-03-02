package appguarden.apppal.evaluation;

import appguarden.apppal.language.Assertion;

/**
 * Class for doing the actual evaluation
 */
public class Evaluate
{
  public final AC ac;
  public final Assertion query;

  public Evaluate(AC ac, Assertion query)
  {
    this.ac = ac;
    this.query = query;
  }
}
