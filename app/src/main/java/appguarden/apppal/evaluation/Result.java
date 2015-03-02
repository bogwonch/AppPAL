package appguarden.apppal.evaluation;

import java.util.NoSuchElementException;

import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.D;

/**
 * Links a query and a delegation depth to a proof.
 */
public class Result
{
  private boolean finished;
  private Proof proof;
  public final Assertion query;
  public final D d;

  public Result(Assertion query, D d)
  {
    this.query = query;
    this.d = d;
    this.finished = false;
    this.proof = null;
  }

  public Result(Assertion query, D d, Proof proof)
  {
    this.query = query;
    this.d = d;
    this.finished = true;
    this.proof = proof;
  }

  public boolean isFinished() { return this.finished; }
  public boolean needsEval() { return ! this.isFinished(); }

  public boolean answers(Assertion query, D d)
  { return (this.query.equals(query) && d.isAtLeast(this.d)); }

  public Proof getProof() throws NoSuchElementException
  {
    if (this.isFinished() && this.proof != null)
      return this.proof;

    throw new NoSuchElementException("no proof available");
  }

  public void provenBy(Proof proof) throws IllegalArgumentException
  {
    if (this.needsEval())
    {
      this.proof = proof;
      this.finished = true;
    }
    else
      throw new IllegalArgumentException("assertion is already proven");
  }
}
