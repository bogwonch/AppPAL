package appguarden.apppal.evaluation;

import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.D;

/**
 * Class for doing the actual evaluation
 */
public class Evaluation
{
  public final AC ac;
  private Assertion q;
  private ResultsTable rt;

  public Evaluation(AC ac)
  {
    this.ac = ac;
    this.rt = new ResultsTable();
  }

  public Result run(final Assertion query)
  { return this.evaluate(query, D.INF); }

  private Result evaluate(Assertion q, D d)
  {
    if (this.rt.has(q,d)) return this.rt.get(q,d);

    this.rt.markUnfinished(q, d);
    Proof condProof = this.cond(q, d);

    if (condProof != null)
    {
      Result result = new Result(q, d, condProof);
      this.rt.update(result);
      return result;
    }

    Result result = new Result(q, d, new Proof(false));
    this.rt.update(result);
    return result;
  }

  private Proof cond(Assertion q, D d)
  {
    // TODO: implement

    return null;
  }
}
