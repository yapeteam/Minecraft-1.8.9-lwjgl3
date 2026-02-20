package stelix.xfile.reader;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import stelix.xfile.Commons;
import stelix.xfile.SxfBlock;
import stelix.xfile.SxfStruct;
import stelix.xfile.gen.SxfLexer;
import stelix.xfile.gen.SxfParser;

public class SxfReader {

    public static SxfBlock toSxf(String data) {
        SxfParser sxfParser = new SxfParser(new CommonTokenStream(new SxfLexer(CharStreams.fromString(data))));
        try {
            return readBlock(sxfParser.file().block());
        } catch (Exception ex) {
            return new SxfBlock();
        }
    }

    protected static SxfBlock readBlock(SxfParser.BlockContext block) {
        SxfBlock sxfBlock = new SxfBlock();
        for (ParseTree variable : block.block_elements().children) {
            if (!(variable instanceof SxfParser.VariableContext))
                continue;
            SxfParser.VariableContext rawVariable = (SxfParser.VariableContext) variable;

            if (rawVariable.children.get(0) instanceof SxfParser.Named_basicvarContext) {
                SxfParser.Named_basicvarContext namedVar = (SxfParser.Named_basicvarContext) rawVariable.children.get(0);
                String name = Commons.removeQuotes(namedVar.children.get(0).getText());
                ParseTree value = namedVar.getChild(2);
                if (value instanceof SxfParser.BlockContext) {
                    sxfBlock.variables().put(name, readBlock((SxfParser.BlockContext) value));
                } else {
                    sxfBlock.variables().put(name, readVariable((SxfParser.Variable_typesContext) value));
                }
            } else if (rawVariable.children.get(0) instanceof SxfParser.Unnamed_objectContext) {
                SxfParser.Unnamed_objectContext unnamedObject = (SxfParser.Unnamed_objectContext) rawVariable.getChild(0);

                if (unnamedObject.getChild(0) instanceof SxfParser.BlockContext) {
                    sxfBlock.unmanagedObjects().add(readBlock((SxfParser.BlockContext) unnamedObject.getChild(0)));
                } else {
                    sxfBlock.unmanagedObjects().add(readStruct((SxfParser.StructContext) unnamedObject.getChild(0)));
                }


            }
        }
        return sxfBlock;
    }


    public static SxfStruct readStruct(SxfParser.StructContext structContext) {
        SxfStruct sxfStruct = new SxfStruct();
        for (ParseTree children : structContext.children) {
            if (children instanceof SxfParser.Variable_typesContext) {
                SxfParser.Variable_typesContext variableType = (SxfParser.Variable_typesContext) children;
                sxfStruct.elements().add(readVariable(variableType));
            }
        }
        return sxfStruct;
    }

    protected static Object readVariable(SxfParser.Variable_typesContext varType) {
        ParseTree varTypeRaw = varType.children.get(0);
        if (varTypeRaw instanceof SxfParser.String_literalContext) {
            return Commons.removeQuotes(varTypeRaw.getText());
        } else if (varTypeRaw instanceof SxfParser.NumberContext) {

            ParseTree numberChildren = varTypeRaw.getChild(0);
            if (numberChildren instanceof SxfParser.Var_integerContext) {
                return Integer.parseInt(numberChildren.getText());
            } else {
                return Double.parseDouble(numberChildren.getText());
            }

        } else if (varTypeRaw instanceof SxfParser.BoolContext) {
            ParseTree boolChildren = varType.getChild(0);
            return boolChildren instanceof SxfParser.Var_trueContext;
        } else if (varTypeRaw instanceof SxfParser.StructContext) {
            return readStruct((SxfParser.StructContext) varTypeRaw);
        } else if (varTypeRaw instanceof SxfParser.BlockContext) {
            return readBlock((SxfParser.BlockContext) varTypeRaw);
        } else if (varTypeRaw instanceof SxfParser.Var_nullContext) {
            return null;
        }
        return null;
    }


}