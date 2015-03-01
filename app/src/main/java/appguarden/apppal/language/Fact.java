package appguarden.apppal.language;



/**
 * AppPAL fact
 * E VP
 */
public class Fact {
    public final E subject;
    public final VP object;

    public Fact(E subject, VP object)
    {
        this.subject = subject;
        this.object = object;
    }

    public String toString()
    {
        return this.subject + " " + this.object;
    }
}
