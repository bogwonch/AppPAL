package appguarden.apppal.test;

import android.test.InstrumentationTestCase;

import appguarden.apppal.evaluation.AC;
import appguarden.apppal.evaluation.Evaluation;
import appguarden.apppal.language.Assertion;

/**
 * Tests the evaluation of AppPAL statements
 */
public class EvaluationTest extends InstrumentationTestCase
{
  public void testTrivialCond() throws Exception
  {
    AC ac = new AC(
      "\"a\" says \"b\" exists."
    );

    Assertion q0 = Assertion.parse("\"a\" says \"b\" exists.");
    Assertion q1 = Assertion.parse("\"a\" says \"c\" exists.");
    Assertion q2 = Assertion.parse("\"a\" says X exists.");
    Assertion q3 = Assertion.parse("\"b\" says \"a\" exists.");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
    assertEquals(Evaluation.shows(ac, q2), true);
    assertEquals(Evaluation.shows(ac, q3), false);
  }

  public void testTrivialCanActAs() throws Exception
  {
    AC ac = new AC
      ("\"alice\" says \"sysadmin\" canAccess(\"personnelRecords\").\n"
      +"\"alice\" says \"bob\" can-act-as \"sysadmin\".             \n"
      );

    Assertion q0 = Assertion.parse("\"alice\" says \"bob\" canAccess(\"personnelRecords\").");
    Assertion q1 = Assertion.parse("\"alice\" says \"malory\" canAccess(\"personnelRecords\").");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
  }
}
