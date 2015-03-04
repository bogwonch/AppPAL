package appguarden.apppal.test;

import android.test.InstrumentationTestCase;

import java.io.IOException;

import appguarden.apppal.language.E;
import appguarden.apppal.language.Fact;

/**
 * Tests various aspects of evaluation including unification.
 */
public class EvaluationTest extends InstrumentationTestCase
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
    final Fact f2 = Fact.parse("X likes(\"a\", \"b\", \"a\"");
    final Fact f3 = Fact.parse("X likes(\"a\", \"b\", \"c\"");
    final Fact f4 = Fact.parse("X likes(\"a\", \"b\")");

    assertEquals(f1.unify(f2).toString(), "{ X=>\"a\", Y=>\"b\" }");
    assertEquals(f2.unify(f1).toString(), "{ X=>\"a\", Y=>\"b\" }");
    assertEquals(f1.unify(f3).hasFailed(), true);
    assertEquals(f1.unify(f4).hasFailed(), true);

    final Fact f5 = Fact.parse("\"a\" can-act-as \"a\"");
    final Fact f6 = Fact.parse("X can-act-as Y");

    assertEquals(f5.unify(f6).toString(), "{ X=>\"a\", Y=>\"a\" }");
    assertEquals(f5.unify(f1).hasFailed(), true);
  }
}
