package wyvern.tools.errors;

import java.io.IOException;

import wyvern.target.corewyvernIL.FormalArg;
import wyvern.target.corewyvernIL.decltype.DeclType;
import wyvern.target.corewyvernIL.decltype.DefDeclType;
import wyvern.target.corewyvernIL.type.StructuralType;

public class ErrorUtils {

    public static String visibleMethods(StructuralType t) {
        StringBuilder visibleMethods = new StringBuilder();
        visibleMethods.append("Visible methods:\n");
        boolean hasMethods = false;
        for (DeclType dt : t.getDeclTypes()) {
            if (!(dt instanceof DefDeclType)) continue;
            hasMethods = true;
            DefDeclType method = (DefDeclType)dt;
            visibleMethods.append("  * ");
            visibleMethods.append(method.getName());
            visibleMethods.append("(");
            String sep = "";
            for (FormalArg arg : method.getFormalArgs()) {
                visibleMethods.append(sep);
                try {
                    arg.doPrettyPrint(visibleMethods, "");
                } catch (IOException e) {
                    visibleMethods.append("<IOException in ErrorUtils.java>");
                }
                sep = ", ";
            }
            visibleMethods.append(") -> ");
            visibleMethods.append(method.getRawResultType().toString());
            visibleMethods.append("\n");
        }
        if (!hasMethods) {
            visibleMethods.append("  None\n");
        }
        return visibleMethods.toString();
    }

}
