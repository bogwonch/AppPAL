package appguarden.apppal.grammar;

import java.util.LinkedList;

import appguarden.apppal.language.Assertion;
import appguarden.apppal.language.CanActAs;
import appguarden.apppal.language.CanSay;
import appguarden.apppal.language.Claim;
import appguarden.apppal.language.Constant;
import appguarden.apppal.language.D;
import appguarden.apppal.language.E;
import appguarden.apppal.language.Fact;
import appguarden.apppal.language.Predicate;
import appguarden.apppal.language.VP;
import appguarden.apppal.language.Variable;

import static appguarden.apppal.language.D.*;

public class AppPALEmitter extends AppPALBaseVisitor<Object> {
    /**
     * Visit a parse tree produced by the {@code variable}
     * labeled alternative in {@link appguarden.apppal.grammar.AppPALParser#e}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitVariable(AppPALParser.VariableContext ctx) {
        return new Variable(ctx.VARIABLE().getText());
    }

    /**
     * Visit a parse tree produced by the {@code constant}
     * labeled alternative in {@link AppPALParser#e}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitConstant(AppPALParser.ConstantContext ctx) {
        String s = ctx.CONSTANT().getText();
        if (s.startsWith("\""))
            s = s.substring(1, s.length());
        if (s.endsWith("\""))
            s = s.substring(0, s.length()-1);
        return new Constant(s);
    }

    /**
     * Visit a parse tree produced by the {@code zero}
     * labeled alternative in {@link AppPALParser#d}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitZero(AppPALParser.ZeroContext ctx) {
        return ZERO;
    }

    /**
     * Visit a parse tree produced by the {@code inf}
     * labeled alternative in {@link AppPALParser#d}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitInf(AppPALParser.InfContext ctx) {
        return INF;
    }

    /**
     * Visit a parse tree produced by the {@code predicate}
     * labeled alternative in {@link AppPALParser#vp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitPredicate(AppPALParser.PredicateContext ctx) {
        String predicate = ctx.PREDICATE_NAME().getText();
        LinkedList<E> args = new LinkedList<>();
        for (AppPALParser.EContext ectx : ctx.e()) {
            E e = (appguarden.apppal.language.E) this.visit(ectx);
            args.add(e);
        }
        return new Predicate(predicate, args);
    }

    /**
     * Visit a parse tree produced by the {@code canSay}
     * labeled alternative in {@link AppPALParser#vp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitCanSay(AppPALParser.CanSayContext ctx) {
        D d = (appguarden.apppal.language.D) this.visit(ctx.d());
        Fact f = (appguarden.apppal.language.Fact) this.visit(ctx.fact());
        return new CanSay(d, f);
    }

    /**
     * Visit a parse tree produced by the {@code canActAs}
     * labeled alternative in {@link AppPALParser#vp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitCanActAs(AppPALParser.CanActAsContext ctx) {
        E e = (appguarden.apppal.language.E) this.visit(ctx.e());
        return new CanActAs(e);
    }

    /**
     * Visit a parse tree produced by {@link AppPALParser#fact}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitFact(AppPALParser.FactContext ctx) {
        E e = (appguarden.apppal.language.E) this.visit(ctx.e());
        VP vp = (appguarden.apppal.language.VP) this.visit(ctx.vp());
        return new Fact(e, vp);
    }

    /**
     * Visit a parse tree produced by {@link AppPALParser#claim}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitClaim(AppPALParser.ClaimContext ctx)
    {
        Fact subsequent = (appguarden.apppal.language.Fact) this.visit(ctx.fact(0));
        LinkedList<Fact> antecedent = new LinkedList<>();
        for (int i=1; i < ctx.fact().size(); i++)
            antecedent.add((appguarden.apppal.language.Fact) this.visit(ctx.fact(i)));
        // TODO: Constraints
        return new Claim(subsequent, antecedent);
    }

    /**
     * Visit a parse tree produced by {@link AppPALParser#assertion}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitAssertion(AppPALParser.AssertionContext ctx) {
        E e = (appguarden.apppal.language.E) this.visit(ctx.e());
        Claim c = (appguarden.apppal.language.Claim) this.visit(ctx.claim());
        return new Assertion(e, c);
    }

    /**
     * Visit a parse tree produced by {@link AppPALParser#ac}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitAc(AppPALParser.AcContext ctx) {
        LinkedList<Assertion> ac = new LinkedList<>();
        for (AppPALParser.AssertionContext actx : ctx.assertion()) {
            ac.add((appguarden.apppal.language.Assertion) this.visit(actx));
        }
        return ac;
    }

    /**
     * Visit a parse tree produced by {@link AppPALParser#c}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Object visitC(AppPALParser.CContext ctx) {
        return null;
    }
}
