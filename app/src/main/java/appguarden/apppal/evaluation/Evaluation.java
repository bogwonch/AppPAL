package appguarden.apppal.evaluation;

import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.Constant;
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

  public static Result run(AC ac, Assertion query)
  {
    return new Evaluation(ac).run(query);
  }

  public Result run(final Assertion query)
  { return this.evaluate(query, D.INF); }

  public static boolean shows(AC ac, Assertion query)
  {
    return new Evaluation(ac).shows(query);
  }

  public boolean shows(final Assertion query)
  { return this.run(query).isProven(); }

  private Result evaluate(Assertion q, D d)
  {
    if (this.rt.has(q,d))
    {
      return this.rt.get(q,d);
    }

    this.rt.markUnfinished(q, d);

    final Proof condProof = this.cond(q, d);
    if (condProof.isKnown())
    {
      final Result result = new Result(q, d, condProof);
      this.rt.update(result);
      return result;
    }

    final Proof canActAsProof = this.canActAs(q, d);
    if (canActAsProof.isKnown())
    {
      final Result result = new Result(q, d, canActAsProof);
      this.rt.update(result);
      return result;
    }

    Result result = new Result(q, d, new Proof(false));
    this.rt.update(result);
    return result;
  }

  private Proof cond(Assertion q, D d)
  {
    for (final Assertion a : this.ac.getAssertions())
    {
      final Unification headU = q.unify(a.consequence());
      if (headU.hasFailed()) continue;

      final Assertion thetaA = a.substitute(headU.theta);

      // Condition three
      if (thetaA.says.consequent.vars().size() != 0)
        continue;

      // TODO: Evaluate the antecedents and constraint.
      return new Proof(true);
    }

    return new Proof(false);
  }

  private Proof canActAs(Assertion q, D d)
  {
    for (final Constant c : this.ac.getConstants())
    {
      final Assertion renaming =
        Assertion.makeCanActAs(q.speaker, q.says.consequent.subject, c);
      final Assertion renamed =
        Assertion.make(q.speaker, c, q.says.consequent.object);

      final Result pRenaming = evaluate(renaming, d);
      if (! pRenaming.isProven()) continue;
      final Result pRenamed = evaluate(renamed, d);
      if (! pRenamed.isProven()) continue;

      return new Proof(true);
    }

    return new Proof(false);
  }
}
