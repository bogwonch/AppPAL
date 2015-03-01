package appguarden.apppal.language;



/**
 * SecPAL can-act-as statement
 * can-act-as E
 *
 */
public class CanActAs extends VP {
    public final E renaming;

    public CanActAs(E renaming)
    {
        this.renaming = renaming;
    }

    public String toString()
    {
        return "can-act-as "+this.renaming;
    }
}
