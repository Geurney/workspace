#include "com_example_dq_surfaceviewexample_MainActivity.h"
int i;
i=255;
/*
 * Class:     com_example_dq_surfaceviewexample_MainActivity
 * Method:    getIntegerFromNative
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_dq_surfaceviewexample_MainActivity_getIntegerFromNative
  (JNIEnv *env, jclass obj){
    return i;
  }

/*
 * Class:     com_example_dq_surfaceviewexample_MainActivity
 * Method:    setIntegerFromJava
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_dq_surfaceviewexample_MainActivity_setIntegerFromJava
  (JNIEnv *env, jclass obj, jint number){
    i = number;
    if(++i>255)
        i=100;
  }

