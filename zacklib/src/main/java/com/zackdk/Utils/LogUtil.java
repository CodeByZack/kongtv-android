package com.zackdk.Utils;/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.text.TextUtils;
import android.util.Log;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 * Author: wyouflf
 * Date: 13-7-24
 * Time: 下午12:23
 */
public class LogUtil {

    public static String customTagPrefix = "";

    private LogUtil() {
    }

    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static CustomLogger customLogger;

    public interface CustomLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }

    public static void d(String content) {
        if (!allowD) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.d(tag, s);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.d(tag, s);
            }
        }
    }

    public static void d(String content, Throwable tr) {
        if (!allowD) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.d(tag, s, tr);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.d(tag, s, tr);
            }
        }
    }

    public static void e(String content) {
        if (!allowE) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.e(tag, s);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.e(tag, s);
            }
        }
    }

    public static void e(String content, Throwable tr) {
        if (!allowE) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.e(tag, s, tr);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.e(tag, s, tr);
            }
        }
    }

    public static void i(String content) {
        if (!allowI) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.i(tag, s);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.i(tag, s);
            }
        }
    }

    public static void i(String content, Throwable tr) {
        if (!allowI) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.i(tag, s, tr);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.i(tag, s, tr);
            }
        }
    }

    public static void v(String content) {
        if (!allowV) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.v(tag, s);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.v(tag, s);
            }
        }
    }

    public static void v(String content, Throwable tr) {
        if (!allowV) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.v(tag, s, tr);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.v(tag, s, tr);
            }
        }
    }

    public static void w(String content) {
        if (!allowW) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.w(tag, s);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.w(tag, s);
            }
        }
    }

    public static void w(String content, Throwable tr) {
        if (!allowW) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.w(tag, s, tr);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.w(tag, s, tr);
            }
        }
    }

    public static void w(Throwable tr) {
        if (!allowW) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, tr);
        } else {
            Log.w(tag, tr);
        }
    }


    public static void wtf(String content) {
        if (!allowWtf) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.wtf(tag, s);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.wtf(tag, s);
            }
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (!allowWtf) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            for (String s : splitContent(content)) {
                customLogger.wtf(tag, s, tr);
            }
        } else {
            for (String s : splitContent(content)) {
                Log.wtf(tag, s, tr);
            }
        }
    }

    public static void wtf(Throwable tr) {
        if (!allowWtf) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, tr);
        } else {
            Log.wtf(tag, tr);
        }
    }

    public static void d2(Object... objects) {
        if (!allowD) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (objects != null) {
            final int length = objects.length;
            for (int i = 0; i < length - 1; i++) {
                builder.append(objects[i]).append(" , ");
            }
            builder.append(objects[length - 1]);
        }
        d(builder.toString());
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    /**
     * 当内容太长时，对内容进行裁切显示
     *
     * @param content 内容
     * @return
     */
    private static String[] splitContent(String content) {
        final int size = 3000;//每段大小
        int length = content.length();//内容长度
        int times = length % size > 0 ? length / size + 1 : length / size;//需要分为几段
        String[] result = new String[times];
        for (int i = 0; i < times - 1; i++) {//除最后一段的处理
            result[i] = content.substring(i * size, (i + 1) * size);
        }
        int lastFirst = times - 1 >= 0 ? times - 1 : 0;//最后一段开始的位置
        int lastEnd = times * size >= length ? length : times * size;//最后一段结束的位置
        result[times - 1] = content.substring(lastFirst * size, lastEnd);//最后一段的内容
        return result;
    }

    /**
     * 统一设置是否允许显示调试日志
     *
     * @param allow 是否允许
     */
    public static void allAllow(boolean allow) {
        allowD = allow;
        allowE = allow;
        allowI = allow;
        allowV = allow;
        allowW = allow;
        allowWtf = allow;
    }
}
