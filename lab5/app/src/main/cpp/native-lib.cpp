#include <jni.h>
#include <string>

extern "C"
JNIEXPORT void JNICALL
Java_com_example_lab5_MainActivity_sortArray(JNIEnv *env, jobject thiz, jintArray array) {
    int length = env->GetArrayLength(array);
    jint *arrayElements = env->GetIntArrayElements(array,NULL);

    // bubble sort
    for (int i = 0; i < length; i++) {
        for (int j = 0; j < length - i - 1; j++) {
            if (arrayElements[j] > arrayElements[j + 1]) {
                std::swap(arrayElements[j], arrayElements[j + 1]);
            }
        }
    }

    env->ReleaseIntArrayElements(array,arrayElements,0);
}