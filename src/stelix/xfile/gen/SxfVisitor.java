// Generated from /home/slowcheetah/work_dir/sxf/src/Sxf.g4 by ANTLR 4.9.2
package stelix.xfile.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SxfParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SxfVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SxfParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(SxfParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SxfParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(SxfParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#named_basicvar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamed_basicvar(SxfParser.Named_basicvarContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#unnamed_object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnnamed_object(SxfParser.Unnamed_objectContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#block_elements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_elements(SxfParser.Block_elementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#variable_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_types(SxfParser.Variable_typesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(SxfParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct(SxfParser.StructContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#var_integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_integer(SxfParser.Var_integerContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#var_double}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_double(SxfParser.Var_doubleContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#var_false}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_false(SxfParser.Var_falseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#var_true}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_true(SxfParser.Var_trueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#var_null}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_null(SxfParser.Var_nullContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(SxfParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link SxfParser#string_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_literal(SxfParser.String_literalContext ctx);
}