#include<jni/com_jme3_alloc_util_NativeBufferUtils.h>
#include<jmealloc.h>

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memoryAlloc
  (JNIEnv* env, jclass clazz, jlong size) {
    return memoryAlloc(env, size);
}

JNIEXPORT void JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memorySet
  (JNIEnv* env, jclass clazz, jobject buffer, jint value, jlong size) {
    memorySet(env, &buffer, value, size);
}

JNIEXPORT void JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memoryMove
  (JNIEnv* env, jclass clazz, jobject to, jobject from, jlong size) {
    memoryMove(env, &to, &from, size);
}

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_clearAlloc
  (JNIEnv* env, jclass clazz, jlong size) {
    return clearAlloc(env, size);
}

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_reAlloc
  (JNIEnv* env, jclass clazz, jobject buffer, jlong size) {
    return reAlloc(env, &buffer, size);
}

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_reAllocArray
  (JNIEnv* env, jclass clazz, jobject buffer, jlong count, jlong size) {
    return reAllocArray(env, &buffer, count, size);
}

JNIEXPORT jobject JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_alignedAlloc
  (JNIEnv* env, jclass clazz, jlong alignment, jlong size) {
    return alignedAlloc(env, alignment, size);
}

JNIEXPORT jint JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_memoryAllocInfo
  (JNIEnv* env, jclass clazz, jint options, jstring outputpath) {
    return 0;
}

JNIEXPORT void JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_destroy
  (JNIEnv* env, jclass clazz, jobject buffer) {
    destroy(env, &buffer);
}

JNIEXPORT jlong JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_getBufferCapacity
  (JNIEnv* env, jclass clazz, jobject buffer) {
    return getBufferCapacity(env, &buffer);
}

JNIEXPORT jlong JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_getBufferSize
  (JNIEnv* env, jclass clazz, jobject buffer) {
    return getBufferSize(env, &buffer);
}

JNIEXPORT jlong JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_getMemoryAdress
  (JNIEnv* env, jclass clazz, jobject buffer) {
    return *((long*) getMemoryAddress(env, &buffer));
}

JNIEXPORT jint JNICALL Java_com_jme3_alloc_util_NativeBufferUtils_mallocStats
  (JNIEnv* env, jclass clazz) {
    return 0;
}