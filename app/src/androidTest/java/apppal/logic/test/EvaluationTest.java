package apppal.logic.test;

import android.test.InstrumentationTestCase;

import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Evaluation;
import apppal.logic.language.Assertion;

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

  public void testCanActAs() throws Exception
  {
    AC ac = new AC
      ("\"alice\" says \"sysadmin\" canAccess(\"personnelRecords\").\n"
        + "\"alice\" says \"bob\" can-act-as \"sysadmin\".             \n"
      );

    Assertion q0 = Assertion.parse("\"alice\" says \"bob\" canAccess(\"personnelRecords\").");
    Assertion q1 = Assertion.parse("\"alice\" says \"malory\" canAccess(\"personnelRecords\").");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
  }

  public void testCanSay0() throws Exception
  {
    AC ac = new AC
      ("\"a\" says \"b\" can-say 0 X good.   \n"
        + "\"b\" says \"app\" good.             \n"
        + "\"b\" says \"c\" can-say 0 X good. \n"
        + "\"c\" says \"other-app\" good.       \n"
      );

    Assertion q0 = Assertion.parse("\"a\" says \"app\" good.");
    Assertion q1 = Assertion.parse("\"c\" says \"app\" good.");
    Assertion q2 = Assertion.parse("\"a\" says \"other-app\" good.");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
    assertEquals(Evaluation.shows(ac, q2), false);
  }

  public void testCanSayInf() throws Exception
  {
    AC ac = new AC
      ("\"a\" says \"b\" can-say inf X good. \n"
        + "\"b\" says \"c\" can-say 0 X good.   \n"
        + "\"c\" says \"app\" good.             \n"
      );

    Assertion q0 = Assertion.parse("\"a\" says \"app\" good.");
    assertEquals(Evaluation.shows(ac, q0), true);
  }

  public void testCondNoVars() throws Exception
  {
    AC ac = new AC
      ("\"a\" says X isCool if X likesJazz. \n"
        + "\"a\" says \"b\" likesJazz.         \n"
      );

    Assertion q0 = Assertion.parse("\"a\" says \"b\" isCool.");
    Assertion q1 = Assertion.parse("\"a\" says \"c\" isCool.");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
  }

  public void testCondVars1() throws Exception
  {
    AC ac = new AC
      ("\"a\" says X isCool if X likes(Y), Y likesJazz. \n"
        + "\"a\" says \"c\" likesJazz.                     \n"
        + "\"a\" says \"b\" likes(\"c\").                  \n"
      );

    Assertion q0 = Assertion.parse("\"a\" says \"b\" isCool.");
    Assertion q1 = Assertion.parse("\"a\" says \"c\" isCool.");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
  }

  public void testCondVars2() throws Exception
  {
    AC ac = new AC
      ("\"a\" says X isCool if X likes(Y,Z), Y likesJazz, Z likesJazz. \n"
        + "\"a\" says \"c\" likesJazz.                                  \n"
        + "\"a\" says \"b\" likes(\"c\",\"d\").                         \n"
        + "\"a\" says \"d\" likesJazz.                                  \n"
        + "\"a\" says \"e\" likesJazz.                                  \n"
        + "\"a\" says \"f\" likesJazz.                                  \n"
      );

    Assertion q0 = Assertion.parse("\"a\" says \"b\" isCool.");
    Assertion q1 = Assertion.parse("\"a\" says \"c\" isCool.");

    assertEquals(Evaluation.shows(ac, q0), true);
    assertEquals(Evaluation.shows(ac, q1), false);
  }

  /**
   * Infinite loop bugs from Haskell SecPAL implementation
   * @throws Exception
   */
  public void testInfLoop() throws Exception
  {
    AC ac0 = new AC
      ( "\"a\" says \"b\" can-say inf \"i\" amCool. \n"
      + "\"b\" says \"a\" can-say inf \"i\" amCool. \n"
      );

    Assertion q0 = Assertion.parse("\"a\" says \"i\" amCool.");
    assertEquals(Evaluation.shows(ac0, q0), false);

    AC ac1 = new AC
      ("\"a\" says \"a\" is if \"a\" is. \n"
      );

    Assertion q1 = Assertion.parse("\"a\" says \"a\" is.");
    assertEquals(Evaluation.shows(ac1, q1), false);
  }

  /**
   * Renaming bug from Haskell SecPAL implementation
   * @throws Exception
   */
  public void testRenaming() throws Exception
  {
    AC ac0 = new AC
      ( "\"i\" says \"a\" isCool if X isCool, X isRad. \n"
      + "\"i\" says \"b\" isCool.                      \n"
      + "\"i\" says \"c\" isRad.                       \n"
      );

    Assertion q0 = Assertion.parse("\"i\" says \"a\" isCool.");
    assertEquals(Evaluation.shows(ac0, q0), false);
  }

  /**
   * CanSay interference bug from Haskell SecPAL implementation
   * @throws Exception
   */
  public void testCanSayInterference() throws Exception
  {
    AC ac0 = new AC
      ( "\"i\" says \"u\" can-say 0 A can(B).                             \n"
      + "\"u\" says \"a\" can(\"x\").                                     \n"
      + "\"u\" says \"a\" can(\"y\").                                     \n"
      + "\"i\" says \"this\" works if \"a\" can(\"x\"), \"a\" can(\"y\"). \n"
      );

    Assertion q0 = Assertion.parse("\"i\" says \"this\" works.");
    assertEquals(Evaluation.shows(ac0, q0), true);
  }

  /**
   * "Odd" bug from Haskell SecPAL implementation
   * Not sure what I was trying to say here as line 2 is unsafe.
   * Checking it throws the exception.
   * @throws Exception
   */
  public void testOdd() throws Exception
  {
    try
    {
      AC ac0 = new AC
        ("\"i\" says X g if X l(\"a\"), X l(\"b\"). \n"
          + "\"i\" says X l(Y) if Y c.              \n"
          + "\"i\" says \"a\" c.                    \n"
          + "\"i\" says \"b\" c.                    \n"
        );
    }
    catch (IllegalArgumentException e)
    {
      return;
    }

    throw new Exception("didn't spot unsafe assertion");
  }

  public void testEqualityE() throws Exception
  {
    AC ac0 = new AC
      ( "\"a\" says X isCool" +
        "  if X likes(Y), " +
        "     X likes(Z), " +
        "     Y likesJazz, " +
        "     Z likesJazz " +
        " where ! Y = Z."
      + "\"a\" says \"b\" likes(\"c\")                                \n"
      + "\"a\" says \"b\" likes(\"d\")                                \n"
      + "\"a\" says \"c\" likesJazz.                                  \n"
      + "\"a\" says \"d\" likesJazz.                                  \n"
      + "\"a\" says \"e\" likesJazz.                                  \n"
      + "\"a\" says \"f\" likesJazz.                                  \n"
    );

    AC ac1 = new AC
      ( "\"a\" says X isCool " +
        "   if X likes(Y), " +
        "      X likes(Z), " +
        "      Y likesJazz, " +
        "      Z likesJazz " +
        "  where ! Y = Z. \n"
        + "\"a\" says \"b\" likes(\"c\")                                \n"
        + "\"a\" says \"c\" likesJazz.                                  \n"
        + "\"a\" says \"d\" likesJazz.                                  \n"
        + "\"a\" says \"e\" likesJazz.                                  \n"
        + "\"a\" says \"f\" likesJazz.                                  \n"
      );

    Assertion q = Assertion.parse("\"a\" says \"b\" isCool.");

    assertEquals(Evaluation.shows(ac0, q), true);
    assertEquals(Evaluation.shows(ac1, q), false);
  }

  public void testEqualityBool() throws Exception
  {
    AC ac = new AC
      ( "\"a\" says \"a\" notOk where true = false."
      + "\"a\" says \"a\" ok    where true = true. "
      );

    Assertion q0 = Assertion.parse("\"a\" says \"a\" notOk.");
    Assertion q1 = Assertion.parse("\"a\" says \"a\" ok.");

    assertEquals(Evaluation.shows(ac, q0), false);
    assertEquals(Evaluation.shows(ac, q1), true);
  }
}
