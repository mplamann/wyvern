package wyvern.target.oir.expressions;

import wyvern.target.oir.ASTVisitor;
import wyvern.target.oir.OIREnvironment;
import wyvern.target.oir.declarations.OIRFloatType;
import wyvern.target.oir.declarations.OIRType;

public class OIRFloat extends OIRLiteral implements OIRValue {
	private float value;

	public OIRFloat(float value) {
		super();
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public OIRType typeCheck(OIREnvironment oirEnv) {
		setExprType (OIRFloatType.getFloatType());
		return getExprType ();
	}

	@Override
	public <S, T> T acceptVisitor(ASTVisitor<S, T> visitor, S state) {
		return visitor.visit(state, this);
	}
}
