package linchange.example.com.waimain.utils;

import android.content.Context;
import android.content.SharedPreferences;

//SharedPreferences工具类
public class ShareUtils {
    public static final String NAME = "config";

    /**
     * 存入字符串
     * @param mContext 上下文
     * @param key 关键字
     * @param value 传入的值
     */
    public static void putString(Context mContext, String key, String value) {
        getSP(mContext).edit().putString(key, value).commit();
    }

    /**
     * 获取字符串
     * @param mContext 上下文
     * @param key 关键字
     * @param defValue 默认返回值
     */
    public static String getString(Context mContext, String key, String defValue) {
        return getSP(mContext).getString(key, defValue);
    }

    /**
     * 返回一个根据上下文生成的SharedPreferences
     * @param mContext 上下文
     * @return 根据上下文生成的SharedPreferences
     */
    private static SharedPreferences getSP(Context mContext) {
        return mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }
}
