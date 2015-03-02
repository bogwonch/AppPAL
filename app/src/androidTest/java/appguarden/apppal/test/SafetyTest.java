package appguarden.apppal.test;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.CanSay;
import appguarden.apppal.language.Claim;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.D;
import appguarden.apppal.language.E;
import appguarden.apppal.language.Fact;
import appguarden.apppal.language.Predicate;
import appguarden.apppal.language.VP;
import appguarden.apppal.language.Variable;

/**
 * Testing AppPAL safety conditions and surrounding code.
 */
public class SafetyTest extends InstrumentationTestCase
{
  public void testFlatness() throws Exception
  {
    E e = new Constant("Alice");
    VP pred = new Predicate("isCool");
    Fact flatFact = new Fact(e, pred);
    VP canSay = new CanSay(D.ZERO, flatFact);
    Fact notFlatFact = new Fact(e, canSay);

    assertEquals(flatFact.isFlat(), true);
    assertEquals(notFlatFact.isFlat(), false);
  }

  /**
   * Tests the assertion safety conditions using the examples from Becker's SecPAL paper.
   * @throws Exception
   */
  public void testSafety() throws Exception
  {
    /*
      These assertion are safe (from SecPAL paper):

      S1. A says B can-read Foo
      S2. A says B can-read Foo if B can x y
      S3. A says B can-read Foo if B can x y, x̸=y
      S4. A says B can x y if B can x y
      S5. A says z can x y if z can x Foo, z can-read y
      S6. A says B can-say 0 x can y z
     */
    Assertion s1 = Assertion.parse("\"a\" says \"b\" canRead(\"foo\").");
    Assertion s2 = Assertion.parse("\"a\" says \"b\" canRead(\"foo\") if \"b\" can(X,Y).");
    Assertion s3 = Assertion.parse("\"a\" says \"b\" canRead(\"foo\") if \"b\" can(X,Y) where ! X = Y.");
    Assertion s4 = Assertion.parse("\"a\" says \"b\" can(X,Y) if \"b\" can(X,Y).");
    Assertion s5 = Assertion.parse("\"a\" says Z can(X,Y) if Z can(X,\"foo\"), Z canRead(Y).");
    Assertion s6 = Assertion.parse("\"a\" says \"b\" can-say 0 X can(Y,Z).");

    assertEquals(s1.isSafe(), true);
    assertEquals(s2.isSafe(), true);
    assertEquals(s3.isSafe(), true);
    assertEquals(s4.isSafe(), true);
    assertEquals(s5.isSafe(), true);
    assertEquals(s6.isSafe(), true);

    /* These Assertions are unsafe (from paper)
      U1. A says B can x Foo
      U2. A says z can-read Foo if B can x y
      U3. A says B can-read Foo if B can x y, w̸=y
      U4. A says B can x y if B can-say 0 C can x y
      U5. A says w can-say 0 x can y z
    */
    Assertion u1 = Assertion.parse("\"a\" says \"b\" can(X,\"foo\").");
    Assertion u2 = Assertion.parse("\"a\" says Z canRead(\"foo\") if \"b\" can(X,Y).");
    Assertion u3 = Assertion.parse("\"a\" says \"b\" canRead(\"foo\") if \"b\" can(X,Y) where ! W = Y.");
    Assertion u4 = Assertion.parse("\"a\" says \"b\" can(X,Y) if \"b\" can-say 0 \"c\" can(X,Y).");
    Assertion u5 = Assertion.parse("\"a\" says W can-say 0 X can(Y,Z).");

    assertEquals(u1.isSafe(), false);
    assertEquals(u2.isSafe(), false);
    assertEquals(u3.isSafe(), false);
    assertEquals(u4.isSafe(), false);
    assertEquals(u5.isSafe(), false);
  }

  /**
   * Check that we can extract the entities we would expect to from an assertion.
   * @throws Exception
   */
  public void testEntityHolding() throws Exception
  {
    Assertion assertion = Assertion.parse("\"a\" says \"b\" canRead(\"foo\") if \"b\" can(X,Y) where ! X = Y.");

    Set<Constant> consts = new HashSet<>();
    consts.add(Constant.parse("\"a\""));
    consts.add(Constant.parse("\"b\""));
    consts.add(Constant.parse("\"foo\""));

    Set<Variable> vars = new HashSet<>();
    vars.add(Variable.parse("X"));
    vars.add(Variable.parse("Y"));

    assertEquals(consts, assertion.consts());
    assertEquals(vars, assertion.vars());

    Set<Variable> empty = new HashSet<>();
    assertNotSame(empty, vars);
  }
}
