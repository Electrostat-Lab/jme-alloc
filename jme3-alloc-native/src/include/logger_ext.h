#ifndef _LOGGER_EXTENSION
#define _LOGGER_EXTENSION

#include<logger.h>
#include<string_utils.h>

/**
 * A quick implementation to log the allocation of a buffer with size on the [stdout] of level [info].
 * 
 * @param address the newly created buffer address to log
 * @param size the size of the newly created buffer
 */
static inline void LOGI_ALLOCMEM(const char* msg, void* memAddress) {
    #ifdef __ENABLE_LOGGER
        const char* substrate = pointerToString(memAddress);
        LOGI(2, msg, substrate);
        free((void*) substrate);
    #endif
}

/**
 * A quick implementation to log the allocation of a buffer with size on the [stderr] of level [error].
 * 
 * @param address the newly created buffer address to log
 * @param size the size of the newly created buffer
 */
static inline void LOGE_ALLOCMEM(const char* msg, void* memAddress) {
    #ifdef __ENABLE_LOGGER
        const char* substrate = pointerToString(memAddress);
        LOGE(2, msg, substrate);
        free((void*) substrate);
    #endif
}

/**
 * A quick implementation to log the memory set of a buffer with size on the [stdout] of level [info].
 * 
 * @param address the newly created buffer address to log
 * @param size the size of the newly created buffer
 */
static inline void LOGI_SETMEM(void* address, size_t size, int value) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        const char* bufferValue = intToString(value);
        LOGI(8, "Memory set buffer elements to = ", "[ Address = ", memAddress, ", Size = ", memSize, ", Values = ", bufferValue, " ]");
        free((void*) memAddress);
        free((void*) memSize);
        free((void*) bufferValue);
    #endif
}

/**
 * A quick implementation to log the buffer memory copy from a buffer to another.
 * 
 * @param toSize the size of the (substrate) to-buffer
 * @param toAddress the start address of the (substrate) to-buffer
 * @param fromSize the size of the (subject) from-buffer
 * @param fromAddress the start address of the (subject) from-buffer
 */
static inline void LOGI_COPYMEM(size_t toSize, void* toAddress, size_t fromSize, void* fromAddress) {
    #ifdef __ENABLE_LOGGER
        const char* toMemAddress = pointerToString(toAddress);
        const char* fromMemAddress = pointerToString(fromAddress);
        const char* toMemSize = longToString(toSize);
        const char* fromMemSize = longToString(fromSize);
        LOGI(8, "Copied memory from buffer = [ Address = ", fromMemAddress, ", Size = ", fromMemSize, " to [ Address = ",
             toMemAddress, ", Size = ", fromMemAddress, " ]");
        free((void*) toMemAddress);
        free((void*) fromMemAddress);
        free((void*) toMemSize);
        free((void*) fromMemSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a buffer with size on the [debug-file] of level [debug].
 * 
 * @param address the newly created buffer address to log
 * @param size the size of the newly created buffer
 */
static inline void LOGD_ALLOCMEM(void* address, size_t size) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGD(5, "Allocated a new buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a cleared buffer with size on the [debug-file] of level [debug].
 * 
 * @param address the newly created buffer address to log
 * @param size the size of the newly created buffer
 */
static inline void LOGD_CALLOCMEM(void* address, size_t size) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGD(5, "Allocated a new cleared buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the memory set of a buffer with size on the [debug-file] of level [debug].
 * 
 * @param address the newly created buffer address to log
 * @param size the size of the newly created buffer
 */
static inline void LOGD_SETMEM(void* address, size_t size, int value) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        const char* bufferValue = intToString(value);
        LOGD(8, "Memory set buffer elements to = ", "[ Address = ", memAddress, ", Size = ", memSize, ", Values = ", bufferValue, " ]");
        free((void*) memAddress);
        free((void*) memSize);
        free((void*) bufferValue);
    #endif
}

/**
 * A quick implementation to log the buffer memory copy from a buffer to another.
 * 
 * @param toSize the size of the (substrate) to-buffer
 * @param toAddress the start address of the (substrate) to-buffer
 * @param fromSize the size of the (subject) from-buffer
 * @param fromAddress the start address of the (subject) from-buffer
 */
static inline void LOGD_COPYMEM(size_t toSize, void* toAddress, size_t fromSize, void* fromAddress) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* toMemAddress = pointerToString(toAddress);
        const char* fromMemAddress = pointerToString(fromAddress);
        const char* toMemSize = longToString(toSize);
        const char* fromMemSize = longToString(fromSize);
        LOGD(8, "Copied memory from buffer = [ Address = ", fromMemAddress, ", Size = ", fromMemSize, " to [ Address = ",
             toMemAddress, ", Size = ", toMemSize, " ]");
        free((void*) toMemAddress);
        free((void*) fromMemAddress);
        free((void*) toMemSize);
        free((void*) fromMemSize);
    #endif
}

/**
 * Logs an additional integer value to the [stdout] of level [info].
 * Note: to use a custom log use [LOGI(int, const char* ...)] and convert the values to string.
 * 
 * @param msg a message to log
 * @param value an integer value to log
 */
static inline void ILOGI(const char* msg, int value) {
    #ifdef __ENABLE_LOGGER
        const char* substrate = intToString(value);
        LOGI(2, msg, substrate);
        free((void*) substrate);
    #endif
}

/**
 * Logs an additional long (64-bit) value to the [stdout] of level [info].
 * Note: to use a custom log use [LOGI(int, const char* ...)] and convert the values to string.
 * 
 * @param msg a message to log
 * @param value a long integer value to log
 */
static inline void LLOGI(const char* msg, long long value) {
    #ifdef __ENABLE_LOGGER
        const char* substrate = longToString(value);
        LOGI(2, msg, substrate);
        free((void*) substrate);
    #endif
}

/**
 * Logs an additional integer value to the [debug-file] of level [debug].
 * Note: to use a custom log use [LOGD(int, const char* ...)] and convert the values to string.
 * 
 * @param msg a message to log
 * @param value an integer value to log
 */
static inline void ILOGD(const char* msg, int value) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* substrate = intToString(value);
        LOGD(2, msg, substrate);
        free((void*) substrate);
    #endif
}

/**
 * Logs an additional long (64-bit) value to the [debug-file] of level [debug].
 * Note: to use a custom log use [LOGD(int, const char* ...)] and convert the values to string.
 * 
 * @param msg a message to log
 * @param value a long integer value to log
 */
static inline void LLOGD(const char* msg, long long value) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* substrate = longToString(value);
        LOGD(2, msg, substrate);
        free((void*) substrate);
    #endif
}

#endif 
