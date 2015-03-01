package appguarden.apppal.language;



import java.util.List;

/**
 * AppPAL predicate
 * predicate (E+)
 */
public class Predicate extends VP {
    public final String name;
    public final List<E> args;


    public Predicate(String name)
    {
        this(name, null);
    }

    public Predicate(String name, List<E> args)
    {
        this.name = name;
        this.args = args;
    }

    public String toString()
    {
        // If the predicate list is empty don't display it
        if(this.args == null || this.args.isEmpty())
            return this.name;

        // Else this.args.size() >= 1
        String answer = this.name+"(";
        for (int i = 0; i < this.args.size()-1; i++)
            answer += this.args.get(i)+", ";
        answer += this.args.get(this.args.size()-1)+")";

        return answer;
    }
}
