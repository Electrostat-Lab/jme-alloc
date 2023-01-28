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
 * Fetches and returns the starting address of the memory region referenced by the given direct java.nio.Buffer.
 * This function allows native code to access the same memory region that is accessible to Java code via the buffer object.
 * 
 * @param buffer the java nio buffer object native pointer
 * @return the same memory region that is accessible to Java code via the buffer object
 */
static inline void* getMemoryAddress(JNIEnv* env, jobject* buffer) {
    return (*env)->GetDirectBufferAddress(env, *buffer);
}

static inline jobject memoryAlloc(JNIEnv* env, size_t size) {
	void* buffer = malloc(size);
	return (*env)->NewDirectByteBuffer(env, buffer, size);
}

static inline void memorySet(JNIEnv* env, jobject* buffer, int value, size_t size) {
	void* memAddress = getMemoryAddress(env, buffer);
	memset(memAddress, value, size);
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

static inline jobject reAlloc(JNIEnv* env, jobject* buffer, size_t size) {
	void* newBuffer = ((void*) realloc(getMemoryAddress(env, buffer), size));
	return (*env)->NewDirectByteBuffer(env, newBuffer, size);
}

static inline jobject reAllocArray(JNIEnv* env, jobject* buffer, size_t count, size_t size) {
	void* newBuffer = ((void*) reallocarray(getMemoryAddress(env, buffer), count, size));
	return (*env)->NewDirectByteBuffer(env, newBuffer, size);
}

static inline jobject alignedAlloc(JNIEnv* env, size_t alignment, size_t size) {
	void* buffer = aligned_alloc(alignment, size);
	return (*env)->NewDirectByteBuffer(env, buffer, size);
}

static inline jlong getBufferCapacity(JNIEnv* env, jobject* buffer) {
	return (*env)->GetDirectBufferCapacity(env, *buffer);
}

static inline jlong getBufferSize(JNIEnv* env, jobject* buffer) {
	return sizeof(getMemoryAddress(env, buffer));
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

static inline int memoryAllocInfo(JNIEnv* env, int options, const char* outputPath) {
	/* not implemented yet */
	return 0;
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