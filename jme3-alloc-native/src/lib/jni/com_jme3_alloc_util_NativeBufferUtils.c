#include<jni/com_jme3_alloc_util_NativeBufferUtils.h>
#include<jmealloc.h>

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memoryAlloc
  (JNIEnv* env, jclass clazz, jlong size) {
    return memoryAlloc(env, size);
}

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memorySet
  (JNIEnv* env, jclass clazz, jobject buffer, jint value, jlong size) {
    return *(memorySet(env, &buffer, value, size));
}

JNIEXPORT void JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memoryMove
  (JNIEnv* env, jclass clazz, jobject to, jobject from, jlong size) {
    memoryMove(env, &to, &from, size);
}

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_clearAlloc
  (JNIEnv* env, jclass clazz, jlong size) {
    return clearAlloc(env, size);
}

JNIEXPORT void JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_destroy
  (JNIEnv* env, jclass clazz, jobject buffer) {
    destroy(env, &buffer);
}

JNIEXPORT jlong JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_getMemoryAdress
  (JNIEnv* env, jclass clazz, jobject buffer) {
    return *((long*) getMemoryAddress(env, &buffer));
}