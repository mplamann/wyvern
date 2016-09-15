package wyvern.target.corewyvernIL.astvisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import wyvern.target.corewyvernIL.Case;
import wyvern.target.corewyvernIL.Environment;
import wyvern.target.corewyvernIL.FormalArg;
import wyvern.target.corewyvernIL.binding.Binding;
import wyvern.target.corewyvernIL.decl.Declaration;
import wyvern.target.corewyvernIL.decl.DefDeclaration;
import wyvern.target.corewyvernIL.decl.DelegateDeclaration;
import wyvern.target.corewyvernIL.decl.TypeDeclaration;
import wyvern.target.corewyvernIL.decl.ValDeclaration;
import wyvern.target.corewyvernIL.decl.VarDeclaration;
import wyvern.target.corewyvernIL.decltype.AbstractTypeMember;
import wyvern.target.corewyvernIL.decltype.ConcreteTypeMember;
import wyvern.target.corewyvernIL.decltype.DeclType;
import wyvern.target.corewyvernIL.decltype.DefDeclType;
import wyvern.target.corewyvernIL.decltype.ValDeclType;
import wyvern.target.corewyvernIL.decltype.VarDeclType;
import wyvern.target.corewyvernIL.expression.Bind;
import wyvern.target.corewyvernIL.expression.Cast;
import wyvern.target.corewyvernIL.expression.Expression;
import wyvern.target.corewyvernIL.expression.FFIImport;
import wyvern.target.corewyvernIL.expression.FieldGet;
import wyvern.target.corewyvernIL.expression.FieldSet;
import wyvern.target.corewyvernIL.expression.IExpr;
import wyvern.target.corewyvernIL.expression.IntegerLiteral;
import wyvern.target.corewyvernIL.expression.Let;
import wyvern.target.corewyvernIL.expression.Match;
import wyvern.target.corewyvernIL.expression.MethodCall;
import wyvern.target.corewyvernIL.expression.New;
import wyvern.target.corewyvernIL.expression.Path;
import wyvern.target.corewyvernIL.expression.RationalLiteral;
import wyvern.target.corewyvernIL.expression.StringLiteral;
import wyvern.target.corewyvernIL.expression.Variable;
import wyvern.target.corewyvernIL.support.EmptyTypeContext;
import wyvern.target.corewyvernIL.support.TypeContext;
import wyvern.target.corewyvernIL.support.Util;
import wyvern.target.corewyvernIL.type.CaseType;
import wyvern.target.corewyvernIL.type.DataType;
import wyvern.target.corewyvernIL.type.ExtensibleTagType;
import wyvern.target.corewyvernIL.type.NominalType;
import wyvern.target.corewyvernIL.type.StructuralType;
import wyvern.target.corewyvernIL.type.ValueType;
import wyvern.target.oir.OIRAST;
import wyvern.target.oir.OIREnvironment;
import wyvern.target.oir.OIRProgram;
import wyvern.target.oir.OIRTypeBinding;
import wyvern.target.oir.declarations.OIRClassDeclaration;
import wyvern.target.oir.declarations.OIRDelegate;
import wyvern.target.oir.declarations.OIRFieldDeclaration;
import wyvern.target.oir.declarations.OIRFieldValueInitializePair;
import wyvern.target.oir.declarations.OIRFormalArg;
import wyvern.target.oir.declarations.OIRIntegerType;
import wyvern.target.oir.declarations.OIRInterface;
import wyvern.target.oir.declarations.OIRMemberDeclaration;
import wyvern.target.oir.declarations.OIRMethod;
import wyvern.target.oir.declarations.OIRMethodDeclaration;
import wyvern.target.oir.declarations.OIRMethodDeclarationGroup;
import wyvern.target.oir.declarations.OIRType;
import wyvern.target.oir.expressions.OIRCast;
import wyvern.target.oir.expressions.OIRExpression;
import wyvern.target.oir.expressions.OIRFFIImport;
import wyvern.target.oir.expressions.FFIType;
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
import wyvern.tools.tests.tagTests.TestUtil;

public class EmitOIRVisitor extends ASTVisitor<EmitOIRState, OIRAST> {
  private int classCount = 0;
  private int interfaceCount = 0;

  private String generateClassName ()
  {
    classCount++;
    return "Class"+classCount;
  }

  private String generateInterfaceName ()
  {
    interfaceCount++;
    return "Interface"+interfaceCount;
  }

  public OIRAST visit(EmitOIRState state, New newExpr) {
    OIRClassDeclaration cd;
    ValueType exprType;
    OIRType oirtype;
    OIREnvironment classenv;
    List<OIRMemberDeclaration> oirMemDecls;
    String className;
    List<OIRDelegate> delegates;
    OIRTypeBinding oirTypeBinding;
    OIRExpression oirexpr;
    List<OIRFieldValueInitializePair> fieldValuePairs;
    List<OIRExpression> args;

    // exprType = newExpr.getExprType();
    // if (exprType != null)
    //   oirtype = (OIRType) exprType.acceptVisitor(this, cxt, oirenv);
    classenv = new OIREnvironment (state.env);
    oirMemDecls = new Vector<OIRMemberDeclaration> ();
    delegates = new Vector<OIRDelegate> ();
    fieldValuePairs = new Vector<OIRFieldValueInitializePair> ();
    args = new Vector<OIRExpression> ();

    TypeContext extendedCxt =
        state.cxt.extend("this", newExpr.typeCheck(state.cxt));

    for (Declaration decl : newExpr.getDecls())
    {
      if (decl instanceof DelegateDeclaration)
      {
        OIRDelegate oirdelegate;

        oirdelegate =
            (OIRDelegate)decl.acceptVisitor(this,
                                            new EmitOIRState(extendedCxt,
                                                      classenv));
        delegates.add(oirdelegate);
      }
      else
      {
        OIRMemberDeclaration oirMemDecl;

        oirMemDecl = (OIRMemberDeclaration) decl.acceptVisitor(this,
            new EmitOIRState(extendedCxt, classenv));

        if (decl instanceof VarDeclaration)
        {
          OIRExpression oirvalue;
          OIRFieldValueInitializePair pair;
          VarDeclaration varDecl;

          varDecl = (VarDeclaration)decl;
          oirvalue =
              (OIRExpression) varDecl.getDefinition().acceptVisitor(this,
                new EmitOIRState(extendedCxt, state.env));
          pair = new OIRFieldValueInitializePair (
              (OIRFieldDeclaration)oirMemDecl, oirvalue);
          fieldValuePairs.add (pair);
          args.add(oirvalue);
        }
        else if (decl instanceof ValDeclaration)
        {
          OIRExpression oirvalue;
          OIRFieldValueInitializePair pair;
          ValDeclaration varDecl;

          varDecl = (ValDeclaration)decl;
          oirvalue =
              (OIRExpression) varDecl.getDefinition().acceptVisitor(this,
                new EmitOIRState(extendedCxt, state.env));
          pair = new OIRFieldValueInitializePair (
              (OIRFieldDeclaration)oirMemDecl, oirvalue);
          fieldValuePairs.add (pair);
          args.add(oirvalue);
        }

        classenv.addName(oirMemDecl.getName (), oirMemDecl.getType ());
        oirMemDecls.add(oirMemDecl);
      }
    }

    className = generateClassName ();
    cd = new OIRClassDeclaration (classenv, className, newExpr.getSelfName(),
                                  delegates, oirMemDecls, fieldValuePairs,
                                  newExpr.getFreeVariables());
    state.env.addType(className, cd);
    classenv.addName(newExpr.getSelfName(), cd);
    OIRProgram.program.addTypeDeclaration(cd);
    oirexpr = new OIRNew (args, className);
    oirexpr.copyMetadata(newExpr);

    return oirexpr;
  }

  public OIRAST visit(EmitOIRState state, MethodCall methodCall) {
    OIRExpression oirbody;
    IExpr body;
    List<OIRExpression> args;
    OIRMethodCall oirMethodCall;

    args = new Vector<OIRExpression> ();

    for (IExpr e : methodCall.getArgs())
    {
      args.add ((OIRExpression)e.acceptVisitor(this, state));
    }

    body = methodCall.getObjectExpr();

    oirbody = (OIRExpression)body.acceptVisitor(this, state);
    oirMethodCall = new OIRMethodCall (oirbody,
                                       body.typeCheck(state.cxt),
                                       methodCall.getMethodName(),
                                       args);
    oirMethodCall.copyMetadata(methodCall);

    return oirMethodCall;
  }


  public OIRAST visit(EmitOIRState state, Match match) {
    OIRLet oirParentLet;
    OIRIfThenElse oirIfExpr;
    OIRLet oirThenLet;
    OIRExpression oirElseExpr;
    OIRExpression oirMatchExpr;

    oirMatchExpr =
        (OIRExpression) match.getMatchExpr().acceptVisitor(this, state);
    oirElseExpr =
        (OIRExpression) match.getElseExpr().acceptVisitor(this, state);

    /* Build the let in if let in else let in if chain bottom up */
    for (int i = match.getCases().size() - 1; i >= 0; i--)
    {
      Case matchCase;
      OIRExpression oirTag;
      OIRExpression body;
      OIRExpression condition;
      List<OIRExpression> arg;

      matchCase = match.getCases().get(i);
      body = (OIRExpression)matchCase.getBody().acceptVisitor(this, state);
      oirTag = (OIRExpression)matchCase.getPattern().acceptVisitor(
          this, state);
      oirThenLet = new OIRLet (matchCase.getVarName(), oirMatchExpr, body);
      arg = new Vector<OIRExpression> ();
      arg.add(oirTag);
      condition = new OIRMethodCall (new OIRFieldGet (
          new OIRVariable ("tmp"), "tag"),
          null,
          "isSubtag", arg);
      oirIfExpr = new OIRIfThenElse (condition, oirThenLet, oirElseExpr);
      oirElseExpr = oirIfExpr;
    }

    oirParentLet = new OIRLet ("tmp", oirMatchExpr, oirElseExpr);
    oirParentLet.copyMetadata(match);
    return oirParentLet;
  }


  public OIRAST visit(EmitOIRState state, FieldGet fieldGet) {
    OIRFieldGet oirFieldGet;
    OIRExpression oirObject;
    Expression object;

    object = (Expression) fieldGet.getObjectExpr();
    oirObject = (OIRExpression) object.acceptVisitor(this, state);
    oirFieldGet = new OIRFieldGet (oirObject, fieldGet.getName());
    oirFieldGet.copyMetadata(fieldGet);

    return oirFieldGet;
  }


  public OIRAST visit(EmitOIRState state, Let let) {

    OIRLet oirLet;
    OIRExpression oirToReplace;
    OIRExpression oirInExpr;
    IExpr toReplace;
    IExpr inExpr;

    TypeContext extendedCxt =
        state.cxt.extend(let.getVarName(), let.getVarType());

    toReplace = let.getToReplace();
    oirToReplace = (OIRExpression)toReplace.acceptVisitor(this,
        new EmitOIRState(extendedCxt, state.env));
    state.env.addName(let.getVarName(), null);
    inExpr = let.getInExpr();
    oirInExpr =
        (OIRExpression)inExpr.acceptVisitor(this,
                                            new EmitOIRState(extendedCxt,
                                                      state.env));
    oirLet = new OIRLet (let.getVarName(), oirToReplace, oirInExpr);
    oirLet.copyMetadata(let);

    return oirLet;
  }


  public OIRAST visit(EmitOIRState state, FieldSet fieldSet) {
    OIRFieldSet oirFieldSet;
    OIRExpression oirObject;
    OIRExpression oirToSet;
    IExpr object;
    IExpr toSet;

    object = (Expression) fieldSet.getObjectExpr();
    toSet = fieldSet.getExprToAssign();
    oirObject = (OIRExpression) object.acceptVisitor(this, state);
    oirToSet = (OIRExpression) toSet.acceptVisitor(this, state);
    oirFieldSet = new OIRFieldSet (oirObject, fieldSet.getFieldName(),
        oirToSet);
    oirFieldSet.copyMetadata(fieldSet);

    return oirFieldSet;
  }


  public OIRAST visit(EmitOIRState state, Variable variable) {
    OIRVariable oirVar;
    OIRType oirType;
    ValueType type;

    oirVar = new OIRVariable (variable.getName());
    oirVar.copyMetadata(variable);

    return oirVar;
  }


  public OIRAST visit(EmitOIRState state, Cast cast) {
    OIRCast oirCast;
    OIRType oirType;
    OIRExpression oirExpr;
    IExpr expr;

    expr = cast.getToCastExpr();
    oirExpr = (OIRExpression)expr.acceptVisitor(this, state);
    oirType = (OIRType)cast.getExprType().acceptVisitor(this, state);
    oirCast = new OIRCast (oirExpr, oirType);
    oirCast.copyMetadata(cast);

    return oirCast;
  }


  public OIRAST visit(EmitOIRState state, VarDeclaration varDecl) {
    OIRFieldDeclaration oirMember;
    OIRType type;
    ValueType _type;

    _type = varDecl.getType();
    type = (OIRType)_type.acceptVisitor(this, state);
    oirMember = new OIRFieldDeclaration (varDecl.getName(), type);
    oirMember.copyMetadata(varDecl);

    return oirMember;
  }

  public OIRAST visit(EmitOIRState state, DefDeclaration defDecl) {
    OIRMethodDeclaration oirMethodDecl;
    OIRMethod oirMethod;
    OIRType oirReturnType;
    List<OIRFormalArg> listOIRFormalArgs;
    OIRExpression oirBody;
    OIREnvironment defEnv;
    TypeContext defCxt;

    listOIRFormalArgs = new Vector <OIRFormalArg> ();
    defEnv = new OIREnvironment (state.env);
    defCxt = state.cxt;

    for (FormalArg arg : defDecl.getFormalArgs())
    {
      OIRFormalArg formalArg;

      formalArg =
          (OIRFormalArg) arg.acceptVisitor(this,
                                           new EmitOIRState(state.cxt, state.env));
      defEnv.addName(formalArg.getName(), formalArg.getType());
      defCxt = defCxt.extend(formalArg.getName(), arg.getType());
      listOIRFormalArgs.add(formalArg);
    }

    oirMethodDecl = new OIRMethodDeclaration (null,
        defDecl.getName(), listOIRFormalArgs);
    oirBody = (OIRExpression) defDecl.getBody().acceptVisitor(this,
        new EmitOIRState(defCxt, defEnv));
    oirMethod = new OIRMethod (defEnv, oirMethodDecl, oirBody);
    oirMethod.copyMetadata(defDecl);

    return oirMethod;
  }

  public OIRAST visit(EmitOIRState state, ValDeclaration valDecl) {
    OIRFieldDeclaration oirMember;
    OIRType type;
    ValueType _type;

    _type = valDecl.getType();
    type = (OIRType)_type.acceptVisitor(this, state);
    oirMember = new OIRFieldDeclaration (valDecl.getName(), type, true);
    oirMember.copyMetadata(valDecl);

    return oirMember;
  }


  public OIRAST visit(EmitOIRState state,
      IntegerLiteral integerLiteral) {

      OIRInteger oirInt = new OIRInteger (integerLiteral.getValue());
      oirInt.copyMetadata(integerLiteral);

      return oirInt;
  }


  public OIRAST visit(EmitOIRState state,
      RationalLiteral rational) {
      OIRRational oirRational =
          new OIRRational (rational.getNumerator(),
                           rational.getDenominator());
      oirRational.copyMetadata(rational);

      return oirRational;
  }


  public OIRAST visit(EmitOIRState state,
      FormalArg formalArg) {
    OIRType oirtype;
    OIRFormalArg oirarg;

    // oirtype = (OIRType) formalArg.getType().acceptVisitor(this,
    //     cxt, oirenv);
    oirarg = new OIRFormalArg (formalArg.getName(), null);
    oirarg.copyMetadata(formalArg);

    return oirarg;
  }


  public OIRAST visit(EmitOIRState state,
      VarDeclType varDeclType) {
    OIRInterface oirtype;
    ValueType type;
    OIRMethodDeclarationGroup methoDecls;
    List<OIRFormalArg> args;
    String fieldName;

    fieldName = varDeclType.getName();
    methoDecls = new OIRMethodDeclarationGroup ();
    type = varDeclType.getRawResultType();
    oirtype = (OIRInterface)type.acceptVisitor(this, state);
    methoDecls.addMethodDeclaration(new OIRMethodDeclaration (oirtype,
        "get"+fieldName, null));
    args = new Vector<OIRFormalArg> ();
    args.add(new OIRFormalArg ("_"+fieldName, oirtype));
    methoDecls.addMethodDeclaration(new OIRMethodDeclaration (null,
        "set"+fieldName, args));

    methoDecls.copyMetadata(varDeclType);

    return methoDecls;
  }

  public OIRAST visit(EmitOIRState state,
      ValDeclType valDeclType) {
    OIRInterface oirtype;
    ValueType type;
    OIRMethodDeclaration methodDecl;
    OIRMethodDeclarationGroup methoDecls;

    type = valDeclType.getRawResultType();
    oirtype = (OIRInterface)type.acceptVisitor(this, state);
    methodDecl = new OIRMethodDeclaration (oirtype,
        "set"+valDeclType.getName(), null);
    methoDecls = new OIRMethodDeclarationGroup ();
    methoDecls.addMethodDeclaration(methodDecl);

    methoDecls.copyMetadata(valDeclType);

    return methoDecls;
  }


  public OIRAST visit(EmitOIRState state,
      DefDeclType defDeclType) {
    OIRMethodDeclaration oirMethodDecl;
    OIRType oirReturnType;
    List<OIRFormalArg> listOIRFormalArgs;
    ValueType returnType;
    OIRMethodDeclarationGroup methodDecls;

    listOIRFormalArgs = new Vector <OIRFormalArg> ();

    for (FormalArg arg : defDeclType.getFormalArgs())
    {
      OIRFormalArg formalArg;

      formalArg = (OIRFormalArg) arg.acceptVisitor(this, state);
      listOIRFormalArgs.add(formalArg);
    }

    returnType = defDeclType.getRawResultType();
    oirReturnType = (OIRType)returnType.acceptVisitor(this, state);
    oirMethodDecl = new OIRMethodDeclaration (oirReturnType,
        defDeclType.getName(), listOIRFormalArgs);
    methodDecls = new OIRMethodDeclarationGroup ();
    methodDecls.addMethodDeclaration(oirMethodDecl);

    methodDecls.copyMetadata(defDeclType);

    return methodDecls;
  }


  public OIRAST visit(EmitOIRState state,
      AbstractTypeMember abstractDeclType) {
    OIRType oirtype;
    OIRMethodDeclaration methDecl;
    OIRMethodDeclarationGroup methodDecls;

    oirtype = (OIRType) abstractDeclType.acceptVisitor(this, state);
    methDecl = new OIRMethodDeclaration (oirtype, "get"+abstractDeclType.getName(), null);
    methodDecls = new OIRMethodDeclarationGroup ();
    methodDecls.addMethodDeclaration(methDecl);

    methodDecls.copyMetadata(abstractDeclType);

    return methodDecls;
  }

  public OIRAST visit(EmitOIRState state,
      StructuralType structuralType) {
    OIRInterface oirinterface;
    List<OIRMethodDeclaration> methodDecls;
    String interfaceName;
    OIREnvironment oirInterfaceEnv;

    interfaceName = generateInterfaceName();
    methodDecls = new Vector<OIRMethodDeclaration> ();
    oirInterfaceEnv = new OIREnvironment (state.env);

    for (DeclType declType : structuralType.getDeclTypes())
    {
      OIRMethodDeclarationGroup declTypeGroup;

      OIRAST declAST = declType.acceptVisitor(this, state);
      declTypeGroup = (OIRMethodDeclarationGroup) declAST;
      for (int i = 0; i < declTypeGroup.size(); i++)
      {
        oirInterfaceEnv.addName(declTypeGroup.elementAt(i).getName(),
            declTypeGroup.elementAt(i).getReturnType());
        methodDecls.add (declTypeGroup.elementAt(i));
      }
    }

    oirinterface = new OIRInterface (oirInterfaceEnv, interfaceName,
        structuralType.getSelfName(), methodDecls);
    state.env.addType(interfaceName, oirinterface);
    OIRProgram.program.addTypeDeclaration(oirinterface);

    oirinterface.copyMetadata(structuralType);

    return oirinterface;
  }

  public OIRAST visit(EmitOIRState state, NominalType nominalType) {
    // Note: This code belongs more in the Match case
    // OIRExpression oirfieldget;
    // Path path;

    // path = nominalType.getPath();
    // oirfieldget = (OIRExpression) path.acceptVisitor(this, cxt, oirenv);

    // return new OIRFieldGet (oirfieldget, nominalType.getTypeMember()+"tag");

    StructuralType defaultType =
        new StructuralType("emptyType",
                           new ArrayList<DeclType>());
    OIRAST result = defaultType.acceptVisitor(this, state);
    result.copyMetadata(nominalType);
    return result;

    // TODO: This should also take into account types available in the OIREnvironment
    // TypeContext context = TestUtil.getStandardGenContext();

    // StructuralType st = nominalType.getStructuralType(context,
    //                                                   defaultType);
    // return st.acceptVisitor(this, cxt, oirenv);
  }

  public OIRAST visit(EmitOIRState state, StringLiteral stringLiteral) {
    OIRString oirstring;

    oirstring = new OIRString (stringLiteral.getValue());
    oirstring.copyMetadata(stringLiteral);

    return oirstring;
  }

  public OIRAST visit(EmitOIRState state, DelegateDeclaration delegateDecl) {
    OIRDelegate oirdelegate;
    OIRType oirtype;
    ValueType type;

    oirdelegate = new OIRDelegate (null, delegateDecl.getFieldName());
    oirdelegate.copyMetadata(delegateDecl);

    return oirdelegate;
  }

  @Override
  public OIRAST visit(EmitOIRState state, Bind bind) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public OIRAST visit(EmitOIRState state,
                      ConcreteTypeMember concreteTypeMember) {
      /*OIRType type = (OIRType)concreteTypeMember.getRawResultType()
        .acceptVisitor(this, cxt, oirenv);
        oirenv.addType(concreteTypeMember.getName(), type);

        return type;*/

      OIRMethodDeclarationGroup group = new OIRMethodDeclarationGroup();
      group.copyMetadata(concreteTypeMember);

      return group;
  }

  @Override
  public OIRAST visit(EmitOIRState state,
                      TypeDeclaration typeDecl) {
      // the tag field
      OIRFieldDeclaration fieldDecl =
          new OIRFieldDeclaration(typeDecl.getName()+"tag",
                                  OIRIntegerType.getIntegerType());

      fieldDecl.copyMetadata(typeDecl);
      return fieldDecl;
  }

  @Override
  public OIRAST visit(EmitOIRState state,
                      CaseType caseType) {
    throw new RuntimeException("CaseType -> OIR unimplemented");
  }

  @Override
  public OIRAST visit(EmitOIRState state,
                      ExtensibleTagType extensibleTagType) {
    throw new RuntimeException("ExtensibleTagType -> OIR unimplemented");
  }

  @Override
  public OIRAST visit(EmitOIRState state,
                      DataType dataType) {
    throw new RuntimeException("DataType -> OIR unimplemented");
  }

  @Override
  public OIRAST visit(EmitOIRState state, FFIImport ffiImport) {
    NominalType javaType = new NominalType("system", "java");
    NominalType pythonType = new NominalType("system", "Python");

    OIRFFIImport result;

    if (ffiImport.getFFIType().equals(javaType)) {
      System.out.println("Java FFI!");
      result = new OIRFFIImport(FFIType.JAVA, ffiImport.getPath());
    } else if (ffiImport.getFFIType().equals(pythonType)) {
      result = new OIRFFIImport(FFIType.PYTHON, ffiImport.getPath());
    } else {
      throw new RuntimeException("Unknown FFI type!");
    }

    result.copyMetadata(ffiImport);
    return result;
  }

}
