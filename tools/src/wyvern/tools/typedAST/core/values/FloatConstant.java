package wyvern.tools.typedAST.core.values;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wyvern.target.corewyvernIL.expression.Expression;
import wyvern.target.corewyvernIL.expression.FloatLiteral;
import wyvern.target.corewyvernIL.modules.TypedModuleSpec;
import wyvern.target.corewyvernIL.support.GenContext;
import wyvern.target.corewyvernIL.type.ValueType;
import wyvern.tools.errors.FileLocation;
import wyvern.tools.typedAST.abs.AbstractValue;
import wyvern.tools.typedAST.core.expressions.Invocation;
import wyvern.tools.typedAST.interfaces.CoreAST;
import wyvern.tools.typedAST.interfaces.InvokableValue;
import wyvern.tools.typedAST.interfaces.TypedAST;
import wyvern.tools.typedAST.interfaces.Value;
import wyvern.tools.typedAST.transformers.GenerationEnvironment;
import wyvern.tools.typedAST.transformers.ILWriter;
import wyvern.tools.types.Type;
import wyvern.tools.types.extensions.Float;
import wyvern.tools.util.EvaluationEnvironment;
import wyvern.tools.util.TreeWriter;

public class FloatConstant extends AbstractValue implements InvokableValue, CoreAST {
    private float value;

    @Deprecated
    public FloatConstant(float f) {
        value = f;
    }

    public FloatConstant(float f, FileLocation loc) {
        value = f;
        location = loc;
    }

    @Override
    public Type getType() {
        return new Float();
    }

    public float getValue() {
        return value;
    }

    @Override
    public Value evaluateInvocation(Invocation exp, EvaluationEnvironment env) {
        FloatConstant floatArgValue = null;
        String operator = exp.getOperationName();

        Value argValue = exp.getArgument().evaluate(env);
        if (argValue instanceof StringConstant) {		// int + "str"
            if (operator.equals("+")) {
                return new StringConstant(this.value + ((StringConstant) argValue).getValue());
            } else {
                throw new RuntimeException("forgot to typecheck!");
            }
        } else if (argValue instanceof FloatConstant) {		//int op int
            floatArgValue = (FloatConstant)argValue;
            switch(operator) {
            case "+": return new FloatConstant(value + floatArgValue.value);
            case "-": return new FloatConstant(value - floatArgValue.value);
            case "*": return new FloatConstant(value * floatArgValue.value);
            case "/": try { return new FloatConstant(value / floatArgValue.value); } catch (ArithmeticException e) { throw new RuntimeException(exp.getLocation() + "", e); }
            case ">": return new BooleanConstant(value > floatArgValue.value);
            case "<": return new BooleanConstant(value < floatArgValue.value);
            case ">=": return new BooleanConstant(value >= floatArgValue.value);
            case "<=": return new BooleanConstant(value <= floatArgValue.value);
            case "==": return new BooleanConstant(value == floatArgValue.value);
            case "!=": return new BooleanConstant(value != floatArgValue.value);
            default: throw new RuntimeException("forgot to typecheck!");
            }
        } else {
            //			shouldn't get here
            throw new RuntimeException("forgot to typecheck!");
        }
    }

    private FileLocation location = FileLocation.UNKNOWN;
    public FileLocation getLocation() {
        return this.location;
    }

    @Override
    public Map<String, TypedAST> getChildren() {
        Hashtable<String, TypedAST> children = new Hashtable<>();
        return children;
    }

    @Override
    public TypedAST cloneWithChildren(Map<String, TypedAST> nc) {
        return new FloatConstant(value, location);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FloatConstant))
            return false;
        if (((IntegerConstant) o).getValue() != this.getValue())
            return false;
        return true;
    }

    @Override
    public Expression generateIL(GenContext ctx, ValueType expectedType, List<TypedModuleSpec> dependencies) {
        return new FloatLiteral(value, location);
    }
}
