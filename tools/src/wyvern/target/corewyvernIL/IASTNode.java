package wyvern.target.corewyvernIL;

import wyvern.target.corewyvernIL.astvisitor.ASTVisitor;
import wyvern.tools.errors.HasLocation;

public interface IASTNode extends HasLocation {
    public abstract <S, T> T acceptVisitor (ASTVisitor<S, T> visitor, S state);
}
