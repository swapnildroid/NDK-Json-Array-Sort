#include <jni.h>
#include <string>
#include <android/log.h>
#include <cstdio>
#include <ctime>

#include "nlohmann/json.hpp"

using namespace std;

using json = nlohmann::json;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_swapnil_ndkjsonarraysort_MainActivity_stringFromJNI(
        JNIEnv *env, jobject /* this */) {
    std::string hello = "NDK Sort Json Array";
    return env->NewStringUTF(hello.c_str());
}

namespace ns {
    // a simple struct to model a Person
    struct Person {
        std::string name;
        std::string dob;
        struct tm age{};
    };

    void to_json(json &j, const Person &p) {
        j = json{{"name", p.name},
                 {"dob",  p.dob}};
    }

    void from_json(const json &j, Person &p) {
        j.at("name").get_to(p.name);
        j.at("dob").get_to(p.dob);
        strptime(p.dob.c_str(), "%Y-%m-%d", &p.age);
    }

    bool compare(const Person &p1, const Person &p2) {
        struct tm d1 = p1.age;
        struct tm d2 = p2.age;

        // Different Years
        if (d1.tm_year < d2.tm_year)
            return true;

        // Different Months
        if (d1.tm_year == d2.tm_year && d1.tm_mon < d2.tm_mon)
            return true;
        // Different Days
        if (d1.tm_year == d2.tm_year && d1.tm_mon == d2.tm_mon &&
            d1.tm_mday < d2.tm_mday)
            return true;

        return false;
    }

    void sortPersons(Person arr[], unsigned int n) {
        sort(arr, arr + n, compare);
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_swapnil_ndkjsonarraysort_MainActivity_example(
        JNIEnv *env, jobject jobject1, jstring jstring1) {

    __android_log_print(ANDROID_LOG_DEBUG, "NdkJsonArraySort", "Hello World!");

    jboolean isCopy = 1;
    const char *chars = env->GetStringUTFChars(jstring1, &isCopy);
    auto inputJson = json::parse(chars);

    unsigned int size = inputJson.size();

    ns::Person p[size];

    for (int i = 0; i < size; i++) {
        p[i] = inputJson[i];
    }

    sortPersons(p, size);

    json outJson;

    for (int i = 0; i < size; i++) {
        outJson[i] = p[i];
    }

    __android_log_print(ANDROID_LOG_DEBUG, "NdkJsonArraySort", "Sort End");
    return env->NewStringUTF(outJson.dump().c_str());
}
