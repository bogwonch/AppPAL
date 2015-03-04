package appguarden.apppal.evaluation;

import java.util.EnumSet;

import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.CanSay;
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

    // TODO: refactor this bollox
    final Proof condProof = this.cond(q, d);
    if (condProof.isKnown())
    {
      final Result result = new Result(q, d, condProof);
      this.rt.update(result);
      return result;
    }

    final Proof canSayProof = this.canSay(q, d);
    if (canSayProof.isKnown())
    {
      final Result result = new Result(q, d, canSayProof);
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

  private Proof canSay(Assertion q, D d)
  {
    // Disallow this rule when delegation banned
    if (d == D.ZERO) return new Proof(false);

    // Disallow nested can-say statements
    if (q.says.consequent.object instanceof CanSay) return new Proof(false);

    for (final Constant c : this.ac.getConstants())
    {
      for(final D depth : EnumSet.of(D.ZERO, D.INF))
      {
        final Assertion delegator =
          Assertion.makeCanSay(q.speaker, c, depth, q.says.consequent);
        final Result rDelegator = evaluate(delegator, d);
        if (rDelegator.isProven())
        {
          final Assertion delegation = Assertion.make(c, q.says.consequent);
          final Result rDelegation = evaluate(delegation, depth);
          if (rDelegation.isProven())
            return new Proof(true);
        }
      }
    }

    return new Proof(false);
  }


  private Proof canActAs(Assertion q, D d)
  {
    for (final Constant c : this.ac.getConstants())
    {
      final Assertion renaming =
        Assertion.makeCanActAs(q.speaker, q.says.consequent.subject, c);
      final Result rRenaming = evaluate(renaming, d);
      if (! rRenaming.isProven()) continue;

      final Assertion renamed =
        Assertion.make(q.speaker, c, q.says.consequent.object);
      final Result rRenamed = evaluate(renamed, d);
      if (! rRenamed.isProven()) continue;

      return new Proof(true);
    }

    return new Proof(false);
  }
}
