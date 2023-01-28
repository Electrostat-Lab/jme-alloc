#include<jni/com_jme3_alloc_util_NativeErrno.h>
#include<jmealloc.h>

JNIEXPORT jint JNICALL Java_com_jme3_alloc_util_NativeErrno_getErrorNoMemory
  (JNIEnv* env, jclass clazz) {
    return ENOMEM;
}

JNIEXPORT jint JNICALL Java_com_jme3_alloc_util_NativeErrno_getErrorInvalidValue
  (JNIEnv* env, jclass clazz) {
    return EINVAL;
}

JNIEXPORT jint JNICALL Java_com_jme3_alloc_util_NativeErrno_getErrno
  (JNIEnv* env, jclass clazz) {
    return getErrno();
}
