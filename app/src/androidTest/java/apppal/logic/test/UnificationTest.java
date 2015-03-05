package apppal.logic.test;

import android.test.InstrumentationTestCase;

import apppal.logic.language.Assertion;
import apppal.logic.language.E;
import apppal.logic.language.Fact;

/**
 * Tests various aspects of unification
 */
public class UnificationTest extends InstrumentationTestCase
{
  public void testUnificationE() throws Exception
  {
    final E alice = E.parse("\"alice\"");
    final E also_alice = E.parse("\"alice\"");
    final E bob = E.parse("\"bob\"");
    final E x = E.parse("X");
    final E y = E.parse("Y");

    assertEquals(alice.unify(bob).toString(), "_|_");
    assertEquals(alice.unify(also_alice).toString(), "{}");
    assertEquals(alice.unify(x).toString(), "{ "+x+"=>"+alice+" }");
    assertEquals(x.unify(alice).toString(), "{ "+x+"=>"+alice+" }");
    assertEquals(x.unify(y).toString(), "{ "+x+"=>"+y+" }");
  }

  public void testUnificationFact() throws Exception
  {
    final Fact f1 = Fact.parse("\"a\" likes(X,Y,X)");
    final Fact f2 = Fact.parse("\"a\" likes(\"a\", \"b\", \"a\"");
    final Fact f3 = Fact.parse("\"a\" likes(\"a\", \"b\", \"c\"");
    final Fact f4 = Fact.parse("\"a\" likes(\"a\", \"b\")");

    assertEquals(f1.unify(f2).toString(), "{ Y=>\"b\", X=>\"a\" }");
    assertEquals(f2.unify(f1).toString(), "{ Y=>\"b\", X=>\"a\" }");
    assertEquals(f1.unify(f3).hasFailed(), true);
    assertEquals(f1.unify(f4).hasFailed(), true);

    final Fact f5 = Fact.parse("\"a\" can-act-as \"a\"");
    final Fact f6 = Fact.parse("X can-act-as Y");

    assertEquals(f5.unify(f6).toString(), "{ Y=>\"a\", X=>\"a\" }");
    assertEquals(f5.unify(f1).hasFailed(), true);

    final Fact f7 = Fact.parse("\"a\" can-say 0 Y likes(X)");
    final Fact f8 = Fact.parse("\"a\" can-say 0 \"b\" likes(\"a\")");
    final Fact f9 = Fact.parse("\"a\" can-say inf \"b\" likes(\"a\")");

    assertEquals(f7.unify(f8).toString(), "{ Y=>\"b\", X=>\"a\" }");
    assertEquals(f7.unify(f7).toString(), "{}");
    assertEquals(f7.unify(f9).hasFailed(), true);

    final Fact fA = Fact.parse("Z likes(Y,X,Y).");
    fA.scope(1);
    assertEquals(f1.unify(fA).toString(), "{ Z.1=>\"a\", Y=>X.1, X=>Y.1 }");
  }

  public void testUnificationAssertion() throws Exception
  {
    Assertion.resetScope();
    final Assertion a1 = Assertion.parse("\"a\" says Y is(X).");
    final Assertion a2 = Assertion.parse("\"a\" says \"b\" is(\"a\").");
    final Assertion a3 = Assertion.parse("\"a\" says X is(Y).");

    assertEquals(a1.unify(a2).toString(), "{ X.1=>\"a\", Y.1=>\"b\" }");
    assertEquals(a1.unify(a3).toString(), "{ X.1=>Y.3, Y.1=>X.3 }");

    // TODO: Check constraints unify.
  }
}
