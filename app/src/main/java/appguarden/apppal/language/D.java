package appguarden.apppal.language;


/**
 * Delegation Depth
 */
public enum D
{
  ZERO, INF;

  @Override
  public String toString()
  {
    switch (this)
    {
      case ZERO:
        return "0";
      case INF:
        return "inf";

      default:
        throw new IllegalArgumentException("illegal value of D");
    }
  }
}
