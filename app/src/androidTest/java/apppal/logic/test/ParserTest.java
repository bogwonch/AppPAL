package apppal.logic.test;

import android.test.InstrumentationTestCase;

import apppal.logic.language.Assertion;

/**
 * Parsing test cases
 */
public class ParserTest extends InstrumentationTestCase
{
  public void testParseAssertionSimple() throws Exception
  {
    Assertion.resetScope();
    String str = "\"alice\" says Bob isCool.";
    assertEquals(Assertion.parse(str).toString(),
      "\"alice\" says Bob.1 isCool.");
  }

  public void testParseAssertionArgsAntecedent() throws Exception
  {
    Assertion.resetScope();
    String str = "\"alice\" says Bob can-say 0 \"alice\" likesJazz if Bob likes(\"alice\", \"mallory\").";
    assertEquals(Assertion.parse(str).toString(),
      "\"alice\" says Bob.1 can-say 0 \"alice\" likesJazz if Bob.1 likes(\"alice\", \"mallory\")."
    );
  }

  public void testParseConstraint() throws Exception
  {
    Assertion.resetScope();
    String str = "\"a\" says Y isCool where equal(X, Y) = true, ! equal(Y, X) = false.";
    assertEquals(Assertion.parse(str).toString(),
      "\"a\" says Y.1 isCool where equal(X.1, Y.1) = true, ! equal(Y.1, X.1) = false."
    );
  }
}
