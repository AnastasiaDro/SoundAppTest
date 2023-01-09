#include <jni.h>
#include <string>

//
// Created by a.drogunova on 10.01.2023.
//


extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_soundapptest_sound_SoundManager_startSound(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
