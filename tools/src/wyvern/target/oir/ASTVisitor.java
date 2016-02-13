package wyvern.target.oir;

import wyvern.target.oir.declarations.OIRClassDeclaration;
import wyvern.target.oir.declarations.OIRFieldDeclaration;
import wyvern.target.oir.declarations.OIRInterface;
import wyvern.target.oir.declarations.OIRMethod;
import wyvern.target.oir.declarations.OIRMethodDeclaration;
import wyvern.target.oir.expressions.OIRBoolean;
import wyvern.target.oir.expressions.OIRCast;
import wyvern.target.oir.expressions.OIRFieldGet;
import wyvern.target.oir.expressions.OIRFieldSet;
import wyvern.target.oir.expressions.OIRIfThenElse;
import wyvern.target.oir.expressions.OIRInteger;
import wyvern.target.oir.expressions.OIRLet;
import wyvern.target.oir.expressions.OIRMethodCall;
import wyvern.target.oir.expressions.OIRNew;
import wyvern.target.oir.expressions.OIRRational;
import wyvern.target.oir.expressions.OIRString;
import wyvern.target.oir.expressions.OIRVariable;

public abstract class ASTVisitor<T> {
	public abstract T visit(OIREnvironment oirenv, OIRInteger oirInteger);
	public abstract T visit(OIREnvironment oirenv, OIRBoolean oirBoolean);
	public abstract T visit(OIREnvironment oirenv, OIRCast oirCast);
	public abstract T visit(OIREnvironment oirenv, OIRFieldGet oirFieldGet);
	public abstract T visit(OIREnvironment oirenv, OIRFieldSet oirFieldSet);
	public abstract T visit(OIREnvironment oirenv, OIRIfThenElse oirIfThenElse);
	public abstract T visit(OIREnvironment oirenv, OIRLet oirLet);
	public abstract T visit(OIREnvironment oirenv, OIRMethodCall oirMethodCall);
	public abstract T visit(OIREnvironment oirenv, OIRNew oirNew);
	public abstract T visit(OIREnvironment oirenv, OIRRational oirRational);
	public abstract T visit(OIREnvironment oirenv, OIRString oirString);
	public abstract T visit(OIREnvironment oirenv, OIRVariable oirVariable);
	public abstract T visit(OIREnvironment oirenv, OIRClassDeclaration oirClassDeclaration);
	public abstract T visit(OIREnvironment oirenv, OIRProgram oirProgram);
	public abstract T visit(OIREnvironment oirenv, OIRInterface oirInterface);
	public abstract T visit(OIREnvironment oirenv, OIRFieldDeclaration oirFieldDeclaration);
	public abstract T visit(OIREnvironment oirenv, OIRMethodDeclaration oirMethodDeclaration);
	public abstract T visit(OIREnvironment oirenv, OIRMethod oirMethod);
}
