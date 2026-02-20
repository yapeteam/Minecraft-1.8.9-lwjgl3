// Generated from /home/slowcheetah/work_dir/sxf/src/Sxf.g4 by ANTLR 4.9.2
package stelix.xfile.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SxfParser}.
 */
public interface SxfListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SxfParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(SxfParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(SxfParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SxfParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SxfParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(SxfParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(SxfParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#named_basicvar}.
	 * @param ctx the parse tree
	 */
	void enterNamed_basicvar(SxfParser.Named_basicvarContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#named_basicvar}.
	 * @param ctx the parse tree
	 */
	void exitNamed_basicvar(SxfParser.Named_basicvarContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#unnamed_object}.
	 * @param ctx the parse tree
	 */
	void enterUnnamed_object(SxfParser.Unnamed_objectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#unnamed_object}.
	 * @param ctx the parse tree
	 */
	void exitUnnamed_object(SxfParser.Unnamed_objectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#block_elements}.
	 * @param ctx the parse tree
	 */
	void enterBlock_elements(SxfParser.Block_elementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#block_elements}.
	 * @param ctx the parse tree
	 */
	void exitBlock_elements(SxfParser.Block_elementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#variable_types}.
	 * @param ctx the parse tree
	 */
	void enterVariable_types(SxfParser.Variable_typesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#variable_types}.
	 * @param ctx the parse tree
	 */
	void exitVariable_types(SxfParser.Variable_typesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(SxfParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(SxfParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#struct}.
	 * @param ctx the parse tree
	 */
	void enterStruct(SxfParser.StructContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#struct}.
	 * @param ctx the parse tree
	 */
	void exitStruct(SxfParser.StructContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#var_integer}.
	 * @param ctx the parse tree
	 */
	void enterVar_integer(SxfParser.Var_integerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#var_integer}.
	 * @param ctx the parse tree
	 */
	void exitVar_integer(SxfParser.Var_integerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#var_double}.
	 * @param ctx the parse tree
	 */
	void enterVar_double(SxfParser.Var_doubleContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#var_double}.
	 * @param ctx the parse tree
	 */
	void exitVar_double(SxfParser.Var_doubleContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#var_false}.
	 * @param ctx the parse tree
	 */
	void enterVar_false(SxfParser.Var_falseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#var_false}.
	 * @param ctx the parse tree
	 */
	void exitVar_false(SxfParser.Var_falseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#var_true}.
	 * @param ctx the parse tree
	 */
	void enterVar_true(SxfParser.Var_trueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#var_true}.
	 * @param ctx the parse tree
	 */
	void exitVar_true(SxfParser.Var_trueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#var_null}.
	 * @param ctx the parse tree
	 */
	void enterVar_null(SxfParser.Var_nullContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#var_null}.
	 * @param ctx the parse tree
	 */
	void exitVar_null(SxfParser.Var_nullContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(SxfParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(SxfParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link SxfParser#string_literal}.
	 * @param ctx the parse tree
	 */
	void enterString_literal(SxfParser.String_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SxfParser#string_literal}.
	 * @param ctx the parse tree
	 */
	void exitString_literal(SxfParser.String_literalContext ctx);
}