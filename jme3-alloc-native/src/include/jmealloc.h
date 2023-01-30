/**
 * @file jmealloc.h 
 * @brief provides a jvm implementation for the native direct buffers utilities
 * @version 0.1-a
 * @author pavl_g
 * @copyright jMonkeyEngine
 * @note 
 */
#ifndef _JME_ALLOC
#define _JME_ALLOC

#include<stdlib.h>
#include<string.h>
#include<stdio.h>
#include<inttypes.h>
#include<errno.h>
#include<jni.h>

/**
 * Fetches and returns the starting address of the memory region referenced by the given direct java.nio.ByteBuffer.
 * This function allows native code to access the same memory region that is accessible to Java code via the buffer object.
 * 
 * @param env the local JNIEnv pointer 
 * @param buffer the java nio buffer object native pointer
 * @return the same memory region that is accessible to Java code via the buffer object
 */
static inline void* getMemoryAddress(JNIEnv* env, jobject* buffer) {
    return (*env)->GetDirectBufferAddress(env, *buffer);
}

/**
 * Allocates a direct [java.nio.ByteBuffer] object on the native heap with [size] bytes.
 * 
 * @param env the local JNIEnv pointer 
 * @param size the size or the capacity of the bytebuffer
 */
static inline jobject memoryAlloc(JNIEnv* env, size_t size) {
	void* buffer = malloc(size);
	return (*env)->NewDirectByteBuffer(env, buffer, size);
}

static inline jobject* memorySet(JNIEnv* env, jobject* buffer, int value, size_t size) {
	void* memAddress = getMemoryAddress(env, buffer);
	memset(memAddress, value, size);
	return buffer;
}

static inline void memoryMove(JNIEnv* env, jobject* to, jobject* from, size_t size) {
	void* toMemAddress = getMemoryAddress(env, to);
	void* fromMemAddress = getMemoryAddress(env, from);
	memmove(toMemAddress, fromMemAddress, size);
}

static inline jobject clearAlloc(JNIEnv* env, size_t size) {
	void* buffer = calloc(1, size);
	return (*env)->NewDirectByteBuffer(env, buffer, size);
}

/**
 * Destroys a java nio buffer.
 * 
 * @param buffer a buffer to destroy
 * @return [0] for success, [1] and [1+] otherwise
 */ 
static inline void destroy(JNIEnv* env, jobject* buffer) {
	void* memoryAddress = getMemoryAddress(env, buffer);
	free(memoryAddress);
	memoryAddress = NULL;
}

/**
 * Retrieves the current native error code.
 * 
 * @return the current thrown system error code
 */ 
static inline int getErrno() {
	return errno;
}

#endif