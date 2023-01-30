/**
 * @file com_jme3_alloc_util_NativeErrno.c 
 * @brief provides an implementation for [com_jme3_alloc_util_NativeErrno.c]
 * @author pavl_g
 * @note For more, refer to jni documentation
 * @copyright 
 * Copyright (c) 2009-2022 jMonkeyEngine
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
