/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class wyvern_target_oir_EmitLLVMNative */

#ifndef _Included_wyvern_target_oir_EmitLLVMNative
#define _Included_wyvern_target_oir_EmitLLVMNative
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    oirProgramToLLVMIR
 * Signature: (Lwyvern/target/oir/OIRProgram;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_oirProgramToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    createMainFunction
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_createMainFunction
  (JNIEnv *, jclass);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    functionCreated
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_functionCreated
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    executeLLVMJIT
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_executeLLVMJIT
  (JNIEnv *, jclass);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    letToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRLet;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_letToLLVMIR
  (JNIEnv *, jclass, jobject, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    integerToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRInteger;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_integerToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    booleanToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRBoolean;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_booleanToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    rationalToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRRational;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_rationalToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    stringToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRString;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_stringToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    ifCondExprToLLVMIR
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_ifCondExprToLLVMIR
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    createThenBasicBlock
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_createThenBasicBlock
  (JNIEnv *, jclass);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    createElseBasicBlock
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_createElseBasicBlock
  (JNIEnv *, jclass);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    createMergeBasicBlock
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_createMergeBasicBlock
  (JNIEnv *, jclass, jstring, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    setupThenBasicBlockEmit
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_setupThenBasicBlockEmit
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    emitThenBasicBlock
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_emitThenBasicBlock
  (JNIEnv *, jclass, jstring, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    setupElseBasicBlockEmit
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_setupElseBasicBlockEmit
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    emitElseBasicBlock
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_emitElseBasicBlock
  (JNIEnv *, jclass, jstring, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    emitMergeBasicBlock
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_emitMergeBasicBlock
  (JNIEnv *, jclass, jstring, jstring, jstring, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    createMergeLocalVar
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_createMergeLocalVar
  (JNIEnv *, jclass);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    beginClassStructure
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_beginClassStructure
  (JNIEnv *, jclass, jstring, jstring jSelfName);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    fieldDeclarationToLLVMIR
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_fieldDeclarationToLLVMIR
  (JNIEnv *, jclass, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    endFieldDecls
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_endFieldDecls
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    methodDeclToLLVMIR
 * Signature: (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_methodDeclToLLVMIR
  (JNIEnv *, jclass, jstring, jstring, jobjectArray);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    endClassStructure
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_endClassStructure
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    interfaceToLLVMIR
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_interfaceToLLVMIR
  (JNIEnv *, jclass, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    fieldGetToLLVMIR
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_fieldGetToLLVMIR
  (JNIEnv *, jclass, jstring, jstring, jstring, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    fieldSetToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRFieldSet;)V
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_fieldSetToLLVMIR
(JNIEnv *jnienv, jclass javaclass, jstring jValueName, jstring jObjTypeName, 
   jstring jObjName, jstring jFieldName, jstring jFieldTypeName);
/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    ifThenElseToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRIfThenElse;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_ifThenElseToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    literalToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRLiteral;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_literalToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    methodCallToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRMethodCall;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_methodCallToLLVMIR
  (JNIEnv *, jclass, jobject, jstring, jobjectArray, jstring);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    newToLLVMIR
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_newToLLVMIR
  (JNIEnv *jnienv, jclass javaclass, jstring jClassName, jint classID, jintArray jFieldsToInit, 
   jobjectArray jValueNames, jobjectArray jTypeNames);
/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    valueToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRValue;)V
 */
JNIEXPORT void JNICALL Java_wyvern_target_oir_EmitLLVMNative_valueToLLVMIR
  (JNIEnv *, jclass, jobject);

/*
 * Class:     wyvern_target_oir_EmitLLVMNative
 * Method:    variableToLLVMIR
 * Signature: (Lwyvern/target/oir/expressions/OIRVariable;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_wyvern_target_oir_EmitLLVMNative_variableToLLVMIR
  (JNIEnv *, jclass, jobject);

#ifdef __cplusplus
}
#endif
#endif