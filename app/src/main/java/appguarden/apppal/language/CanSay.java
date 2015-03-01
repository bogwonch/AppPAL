package appguarden.apppal.language;



/**
 * AppPAL can-say statement
 * can-say D Fact
 */
public class CanSay extends VP {
    public final D d;
    public final Fact fact;

    public CanSay(D d, Fact fact)
    {
        this.d = d;
        this.fact = fact;
    }

    public String toString()
    {
        return "can-say "+this.d+" "+this.fact;
    }
}

