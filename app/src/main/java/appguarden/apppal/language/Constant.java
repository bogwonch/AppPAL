package appguarden.apppal.language;

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
}
