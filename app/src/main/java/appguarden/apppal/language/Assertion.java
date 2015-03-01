package appguarden.apppal.language;

/**
 * SecPAL Assertion
 */
public class Assertion {
    public final E speaker;
    public final Claim says;

    public Assertion(E speaker, Claim says)
    {
        this.speaker = speaker;
        this.says = says;
    }

    public String toString()
    {
        return this.speaker + " says " + this.says + ".";
    }
}
