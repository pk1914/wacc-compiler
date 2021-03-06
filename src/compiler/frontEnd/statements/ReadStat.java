package compiler.frontEnd.statements;

import compiler.CodePosition;
import compiler.backEnd.codeGeneration.AbstractSyntaxTreeVisitor;
import compiler.frontEnd.assignables.AssignLHS;
import compiler.frontEnd.errorHandling.SemanticException;
import compiler.frontEnd.types.ArrType;
import compiler.frontEnd.types.BaseType;

public class ReadStat extends Stat {
  
  private final AssignLHS readItem;

  public ReadStat(AssignLHS readItem, CodePosition codePos) {
    super(codePos);
    this.readItem = readItem;
    checkErrors(readItem);
  }

  @Override
  public CodePosition getPosition() {
    return codePos;
  }
  
  private void checkErrors(AssignLHS readItem) {
    if (!readItem.getType().equals(BaseType.typeInt) 
        && !readItem.getType().equals(BaseType.typeChar)
        && (!isString(readItem))) {
      throw new SemanticException("At: " + codePos.toString()  
          + " item to be read can only be an int, char or string."
          + " Actual type: " + readItem.getType());
    }
  }
  
  private boolean isString(AssignLHS readItem) {
    return readItem.getType().equals(new ArrType(BaseType.typeChar));
  }
  
  public AssignLHS getItem() {
    return readItem;
  }
  
  @Override
  public <T> T accept(AbstractSyntaxTreeVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
