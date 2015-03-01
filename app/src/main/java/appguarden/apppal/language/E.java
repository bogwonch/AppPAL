package appguarden.apppal.language;

/**
 * AppPAL entity
 */
public abstract class E {
    public final String name;
    public final EKind kind;

    public E(String name, EKind kind)
    {
        this.name = name;
        this.kind = kind;
    }

    public abstract String toString();
}
