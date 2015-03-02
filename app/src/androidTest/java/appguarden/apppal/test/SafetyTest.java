package appguarden.apppal.test;

import android.test.InstrumentationTestCase;

import java.util.LinkedList;
import java.util.List;

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
    E a = new Constant("A");
    E b = new Constant("B");
    E f = new Constant("Foo");
    E x = new Variable("x");
    E y = new Variable("y");
    E z = new Variable("z");

    List<E> l_foo = new LinkedList<>();
    l_foo.add(f);
    List<E> l_y = new LinkedList<>();
    l_y.add(y);
    List<E> xy = new LinkedList<>();
    xy.add(x);
    xy.add(y);
    List<E> xf = new LinkedList<>();
    xf.add(x);
    xf.add(f);
    List<E> yz = new LinkedList<>();
    yz.add(y);
    yz.add(z);

    VP canReadFoo = new Predicate("can-read", l_foo);
    VP canReadY = new Predicate("can-read", l_y);
    VP canXY = new Predicate("can", xy);
    VP canXF = new Predicate("can", xf);
    VP canYZ = new Predicate("can", yz);

    Fact bCanReadF = new Fact(b, canReadFoo);
    Fact zCanReadY = new Fact(z, canReadY);
    Fact bCanXY = new Fact(b, canXY);
    Fact xCanYZ = new Fact(x, canYZ);
    Fact zCanXY = new Fact(z, canXY);
    Fact zCanXF = new Fact(z, canXF);

    VP canSay0XCanYZ = new CanSay(D.ZERO, xCanYZ);
    Fact bCanSay0XCanYZ = new Fact(b, canSay0XCanYZ);

    List<Fact> a_BCanXY = new LinkedList<>();
    a_BCanXY.add(bCanXY);

    List<Fact> a_zCanXY_zCanReadY = new LinkedList<>();
    a_zCanXY_zCanReadY.add(zCanXF);
    a_zCanXY_zCanReadY.add(zCanReadY);

    Assertion s1 = new Assertion(a, new Claim(bCanReadF));
    Assertion s2 = new Assertion(a, new Claim(bCanReadF, a_BCanXY));
    // TODO: S3
    Assertion s4 = new Assertion(a, new Claim(bCanXY, a_BCanXY));
    Assertion s5 = new Assertion(a, new Claim(zCanXY, a_zCanXY_zCanReadY));
    Assertion s6 = new Assertion(a, new Claim(bCanSay0XCanYZ));

    assertEquals(s1.isSafe(), true);
    assertEquals(s2.isSafe(), true);
    //assertEquals(s3.isSafe(), true);
    assertEquals(s4.isSafe(), true);
    assertEquals(s5.isSafe(), true);
    assertEquals(s6.isSafe(), true);
  }
}
