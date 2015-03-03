package appguarden.apppal.test;

import android.test.InstrumentationTestCase;

import java.io.IOException;

import appguarden.apppal.language.E;

/**
 * Tests various aspects of evaluation including unification.
 */
public class EvaluationTest extends InstrumentationTestCase
{
  public void testUnification() throws Exception
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
}
