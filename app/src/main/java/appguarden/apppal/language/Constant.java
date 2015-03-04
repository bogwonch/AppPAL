package appguarden.apppal.language;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import appguarden.apppal.evaluation.Unification;
import appguarden.apppal.grammar.AppPALEmitter;
import appguarden.apppal.grammar.AppPALLexer;
import appguarden.apppal.grammar.AppPALParser;
import appguarden.apppal.language.constraint.CE;

/**
 * Constant Entity
 */
public class Constant extends E
{
  public Constant(String name)
  {
    super(name, EKind.CONSTANT);
  }

  public String toString()
  {
    return "\"" + this.name + "\"";
  }

  public static Constant parse(String str) throws IOException
  {
    E e = E.parse(str);
    if (e instanceof Constant)
      return (Constant) e;
    else
      throw new IOException("parsed something else when parsing a constant");
  }
}
