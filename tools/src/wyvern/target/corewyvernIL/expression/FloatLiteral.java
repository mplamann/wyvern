package wyvern.target.corewyvernIL.expression;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import wyvern.target.corewyvernIL.Environment;
import wyvern.target.corewyvernIL.astvisitor.ASTVisitor;
import wyvern.target.corewyvernIL.support.TypeContext;
import wyvern.target.corewyvernIL.support.Util;
import wyvern.target.corewyvernIL.type.ValueType;
import wyvern.target.oir.OIREnvironment;
import wyvern.tools.errors.FileLocation;

public class FloatLiteral extends Literal implements Invokable {

    private final float value;

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FloatLiteral other = (FloatLiteral) obj;
        if (value != other.value)
            return false;
        return true;
    }

    public FloatLiteral(float value) {
        this(value, FileLocation.UNKNOWN);
    }
    public FloatLiteral(float value, FileLocation loc) {
        super(Util.floatType(), loc);
        this.value = value;
    }

    @Override
    public void doPrettyPrint(Appendable dest, String indent) throws IOException {
        dest.append(Float.toString(value));
    }

    public float getValue() {
        return value;
    }

    @Override
    public ValueType typeCheck(TypeContext env) {
        return Util.floatType();
    }

    @Override
    public <S, T> T acceptVisitor(ASTVisitor <S, T> emitILVisitor,
                                  S state) {
        return emitILVisitor.visit(state, this);
    }
	
    @Override
    public Set<String> getFreeVariables() {
        return new HashSet<>();
    }


    @Override
    public ValueType getType() {
        return Util.floatType();
    }

    @Override
    public Value invoke(String methodName, List<Value> args) {

        switch (methodName) {
        case "+": return new FloatLiteral(this.value + ((FloatLiteral)args.get(0)).getValue());
        case "-": return new FloatLiteral(this.value - ((FloatLiteral)args.get(0)).getValue());
        case "*": return new FloatLiteral(this.value * ((FloatLiteral)args.get(0)).getValue());
        case "/": return new FloatLiteral(this.value / ((FloatLiteral)args.get(0)).getValue());
        case "%": return new FloatLiteral(this.value % ((FloatLiteral)args.get(0)).getValue());
        case "negate": return new FloatLiteral(-this.value);
        case "<": return new BooleanLiteral(this.value < ((FloatLiteral)args.get(0)).getValue());
        case ">": return new BooleanLiteral(this.value > ((FloatLiteral)args.get(0)).getValue());
        case "==": return new BooleanLiteral(this.value == ((FloatLiteral)args.get(0)).getValue());
        default: throw new RuntimeException("runtime error: float operation " + methodName + "not supported by the runtime");
        }
    }

    @Override
    public Value getField(String fieldName) {
        throw new RuntimeException("no fields");
    }
}
