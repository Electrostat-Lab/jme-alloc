#ifndef _STRING_UTILS
#define _STRING_UTILS

#include<string.h>
#include<stdlib.h>
#include<stdio.h>

static inline const char* pointerToString(void* ptrvalue) {
    const char* str = (const char*) calloc(sizeof(ptrvalue) + 1, sizeof(char));
    sprintf((char*) str, "%p", ptrvalue);
    return str;
}

static inline const char* ulongToString(unsigned long value) {
    const char* str = (const char*) calloc(sizeof(value) + 1, sizeof(char));
    sprintf((char*) str, "%ld", value);
    return str;
} 

static inline const char* longToString(long long value) {
    const char* str = (const char*) calloc(sizeof(value) + 1, sizeof(char));
    sprintf((char*) str, "%lld", value);
    return str;
}

static inline const char* intToString(int value) {
    const char* str = (const char*) calloc(sizeof(value) + 1, sizeof(char));
    sprintf((char*) str, "%d", value);
    return str;
}

static inline const char* floatToString(float value) {
    const char* str = (const char*) calloc(sizeof(value) + 1, sizeof(char));
    sprintf((char*) str, "%f", value);
    return str;
}

static inline const char* doubleToString(double value) {
    const char* str = (const char*) calloc(sizeof(value) + 1, sizeof(char));
    sprintf((char*) str, "%llf", value);
    return str;
}

#endif