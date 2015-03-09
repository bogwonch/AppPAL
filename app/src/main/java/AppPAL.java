import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Evaluation;
import apppal.logic.evaluation.Result;
import apppal.logic.language.Assertion;

/**
 * AppPAL for when we're not using an Android device, and instead just want to run it on a computer.
 */
public class AppPAL
{
  private final AC ac;
  private final Evaluation eval;

  public AppPAL(String[] files)
  {
    this.ac = new AC();
    for (final String path : files)
      try
      { this.ac.merge(new AC(new FileInputStream(path))); }
      catch (IOException e)
      { System.err.println("AppPAL: error: " + e); }

    this.eval = new Evaluation(this.ac);
  }

  public void repl()
  {
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try
    {
      for (;;)
      {
        System.out.print("?- ");

        final String line = br.readLine();
        if (line == null || line.isEmpty()) return;
        final Assertion q = Assertion.parse(line);
        Result r = this.eval.run(q);
        if (r.isProven())
          System.out.println("okay");
        else
          System.err.println("nope");
      }
    }
    catch (IOException e)
    {
      System.err.println("AppPAL: Error: "+e);
    }
  }

  public static void main(String[] args)
  {
    if (args.length == 0)
    {
      System.err.println("usage: java AppPAL policy [policy..]");
    }
    else
      new AppPAL(args).repl();
  }
}
