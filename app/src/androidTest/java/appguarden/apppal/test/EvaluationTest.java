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
    E alice = E.parse("\"alice\"");
    E also_alice = E.parse("\"alice\"");
    E bob = E.parse("\"bob\"");
    E x = E.parse("X");
    E y = E.parse("Y");

    assertEquals(alice.unify(bob).toString(), "_|_");
    assertEquals(alice.unify(also_alice).toString(), "{}");
    assertEquals(alice.unify(x).toString(), "{ "+x+"=>"+alice+" }");
    assertEquals(x.unify(alice).toString(), "{ "+x+"=>"+alice+" }");
    assertEquals(x.unify(y).toString(), "{ "+x+"=>"+y+" }");
  }

  public void testUnificationFact() throws Exception
  {
    Fact f1 = Fact.parse("\"a\" likes(X,Y,X)");
    Fact f2 = Fact.parse("X likes(\"a\", \"b\", \"a\"");
    Fact f3 = Fact.parse("X likes(\"a\", \"b\", \"c\"");

    assertEquals(f1.unify(f2).toString(), "{ X=>\"a\", Y=>\"b\" }");
    assertEquals(f1.unify(f3).toString(), "_|_");


  }
}
