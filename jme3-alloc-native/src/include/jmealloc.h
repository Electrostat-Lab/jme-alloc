/**
 * @file jmealloc.h 
 * @brief provides a jvm implementation for the native direct buffers utilities
 * @author pavl_g
 * @note For more, refer to GNU libc-stdlib and the java.nio documentations.
 * @copyright 
 * Copyright (c) 2009-2023 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
#ifndef _JME_ALLOC
#define _JME_ALLOC

#include<stdlib.h>
#include<string.h>
#include<logger_ext.h>
#include<errno.h>
#include<string_utils.h>    
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
 * Fetches the capacity or the size of the buffer in bytes.
 * 
 * @param env the local JNIEnv pointer 
 * @param buffer the java nio buffer object native pointer
 * @return the buffer capacity in bytes
 */
static inline jlong getBufferCapacity(JNIEnv* env, jobject* buffer) {
    return (*env)->GetDirectBufferCapacity(env, *buffer);
}

/**
 * Allocates a direct [java.nio.ByteBuffer] object on the native heap with [size] bytes.
 * 
 * @param env the local JNIEnv pointer 
 * @param size the size or the capacity of the bytebuffer
 * @return a newly directly allocated jobject representing a java.nio.ByteBuffer
 */
static inline jobject memoryAlloc(JNIEnv* env, size_t size) {
	void* buffer = malloc(size);
	LOGD_ALLOCMEM(DEBUG, buffer, size);
	return (*env)->NewDirectByteBuffer(env, buffer, size);
}

/**
 * Copies the value [value] into [size] byte elements starting from the [buffer] address.
 * 
 * @param env the local JNIEnv pointer 
 * @param buffer a buffer block to set its elements
 * @param value an 8-bit value to set the memory blocks (buffer elements) with, starting from the buffer address
 * @param size the number of byte elements to set
 * @return a pointer to the jobject that represents this ByteBuffer
 */
static inline jobject* memorySet(JNIEnv* env, jobject* buffer, int value, size_t size) {
	void* memAddress = getMemoryAddress(env, buffer);
	memset(memAddress, value, size);
	LOGD_SETMEM(DEBUG, memAddress, size, value);
	return buffer;
}

/**
 * Manipulates the values of byte elements [size] starting from the buffers addresses, from a bytebuffer to another.
 * 
 * @param env the local JNIEnv pointer 
 * @param to the destination direct java.nio.ByteBuffer
 * @param from the source direct java.nio.ByteBuffer
 * @param size the number of elements to move starting from the buffers addresses
 */
static inline void memoryMove(JNIEnv* env, jobject* to, jobject* from, size_t size) {
	void* toMemAddress = getMemoryAddress(env, to);
	void* fromMemAddress = getMemoryAddress(env, from);
	memmove(toMemAddress, fromMemAddress, size);
	LOGD_COPYMEM(DEBUG, size, toMemAddress, size, fromMemAddress);
}

/**
 * Copies the values of byte elements [size] starting from the buffers addresses, from a bytebuffer to another.
 * 
 * @param env the local JNIEnv pointer 
 * @param to the destination direct java.nio.ByteBuffer
 * @param from the source direct java.nio.ByteBuffer
 * @param size the number of elements to move starting from the buffers addresses
 */
static inline void memoryCopy(JNIEnv* env, jobject* to, jobject* from, size_t size) {
	void* toMemAddress = getMemoryAddress(env, to);
	void* fromMemAddress = getMemoryAddress(env, from);
	memcpy(toMemAddress, fromMemAddress, size);
	LOGD_COPYMEM(DEBUG, size, toMemAddress, size, fromMemAddress);
}

/**
 * Allocates a direct [java.nio.ByteBuffer] object on the native heap with [size] bytes and initializes its elements to zero.
 * 
 * @param env the local JNIEnv pointer 
 * @param size the size or the capacity of the bytebuffer
 * @return a newly directly allocated jobject representing a java.nio.ByteBuffer
 */
static inline jobject clearAlloc(JNIEnv* env, size_t size) {
	void* buffer = calloc(1, size);
	LOGD_ALLOCMEM(DEBUG, buffer, size);
	return (*env)->NewDirectByteBuffer(env, buffer, size);
}

/**
 * Destroys a direct java nio buffer.
 * 
 * @param buffer a buffer to destroy
 */ 
static inline void destroy(JNIEnv* env, jobject* buffer) {
	void* memoryAddress = getMemoryAddress(env, buffer);
	LOGD_DESTROYMEM(DEBUG, memoryAddress, getBufferCapacity(env, buffer));
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