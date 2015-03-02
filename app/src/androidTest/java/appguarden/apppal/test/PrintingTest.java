package appguarden.apppal.test;

import android.test.InstrumentationTestCase;

import java.util.LinkedList;

import appguarden.apppal.language.CanActAs;
import appguarden.apppal.language.CanSay;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.D;
import appguarden.apppal.language.E;
import appguarden.apppal.language.Fact;
import appguarden.apppal.language.Predicate;
import appguarden.apppal.language.VP;
import appguarden.apppal.language.Variable;

/**
 * Unit tests that when we print something they appear as expected
 */
public class PrintingTest extends InstrumentationTestCase
{
  public void testPrintE() throws Exception
  {
    final E variable = new Variable("Bob");
    assertEquals(variable.toString(), "Bob");

    final E constant = new Constant("Alice");
    assertEquals(constant.toString(), "\"Alice\"");
  }

  public void testPrintD() throws Exception
  {
    final D zero = D.ZERO;
    assertEquals(zero.toString(), "0");

    final D inf = D.INF;
    assertEquals(inf.toString(), "inf");
  }

  public void testPrintVP() throws Exception
  {
    final E alice = new Constant("Alice");
    final E bob = new Variable("Bob");

    final VP canActAs = new CanActAs(alice);
    assertEquals(canActAs.toString(), "can-act-as \"Alice\"");

    final VP predNoArgs = new Predicate("isCool");
    assertEquals(predNoArgs.toString(), "isCool");

    LinkedList<E> args = new LinkedList<>();
    final VP predAlsoNoArgs = new Predicate("isCool", args);
    assertEquals(predAlsoNoArgs.toString(), "isCool");

    args.add(alice);
    final VP predOneArg = new Predicate("isCool", args);
    assertEquals(predOneArg.toString(), "isCool(\"Alice\")");

    args.add(bob);
    final VP predTwoArg = new Predicate("isCool", args);
    assertEquals(predTwoArg.toString(), "isCool(\"Alice\", Bob)");

    final Fact fact = new Fact(bob, predNoArgs);

    final VP canSay = new CanSay(D.ZERO, fact);
    assertEquals(canSay.toString(), "can-say 0 Bob isCool");
  }

  public void testPrintFact() throws Exception
  {
    final E bob = new Variable("Bob");
    final VP predNoArgs = new Predicate("isCool");
    final Fact fact = new Fact(bob, predNoArgs);
    assertEquals(fact.toString(), "Bob isCool");
  }

  public void testPrintClaim() throws Exception
  {
    // TODO
  }
}
