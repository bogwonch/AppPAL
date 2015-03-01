package appguarden.apppal.language;

/**
 * A variable instance of an entity
 */
public class Variable extends E {

    public Variable(String name)
    {
        super(name, EKind.VARIABLE);
    }

    public String toString()
    {
        return this.name;
    }
}
