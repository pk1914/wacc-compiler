package compiler.literals;

import compiler.CodePosition;
import compiler.errorHandling.SyntaxException;
import compiler.semanticCheck.ReturnableType;
import compiler.types.BaseType;

public class CharLiter implements Liter {
  
  private final String value;
  private final CodePosition codePos;
  
  public CharLiter(String value, CodePosition codePos) throws SyntaxException {
    this.value = value.substring(1, value.length() - 1);
    this.codePos = codePos;
    checkEscapedCharacter(value);
  }

  @Override
  public CodePosition getPosition() {
    return codePos;
  }

  @Override
  public ReturnableType getType() {
    return BaseType.typeChar;
  }

  @Override
  public String getString() {
    return value;
  }
  
  private void checkEscapedCharacter(String value) throws SyntaxException {
    if (value.length() == 1 && value.charAt(0) == '\\') {
      throw new SyntaxException
        ("Invalid escaped character at " + codePos + '.');
    }
    else if (value.charAt(0) == '\\') {
      switch (value.charAt(1)) {
      case ('0'):
      case ('b'):
      case ('t'):
      case ('n'):
      case ('f'):
      case ('r'):
      case ('\\'):
      case ('\''):
      case ('"'):
        break;
      default:
        throw new SyntaxException
          ("Invalid escaped character at " + codePos + '.');
      }
    }
  }

}