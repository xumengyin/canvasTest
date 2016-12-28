LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := mytest
LOCAL_SRC_FILES := com_example_mytest_JniTest.c

include $(BUILD_SHARED_LIBRARY)