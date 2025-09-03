package cn.puehmc.fuckName.core;

import java.util.regex.Pattern;

public class NameUtils {
    
    // 中文字符的Unicode范围
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fff\u3400-\u4dbf\u20000-\u2a6df\u2a700-\u2b73f\u2b740-\u2b81f\u2b820-\u2ceaf\uf900-\ufaff\u3300-\u33ff\ufe30-\ufe4f\uf900-\ufaff\u2f800-\u2fa1f]");
    
    // 英文字符和数字的模式
    private static final Pattern ENGLISH_PATTERN = Pattern.compile("[a-zA-Z0-9]");
    
    // 纯英文字符（不包括数字和特殊字符）
    private static final Pattern PURE_ENGLISH_PATTERN = Pattern.compile("[a-zA-Z]");
    
    /**
     * 检查字符串是否包含中文字符
     * @param text 要检查的文本
     * @return 如果包含中文字符返回true
     */
    public static boolean containsChinese(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return CHINESE_PATTERN.matcher(text).find();
    }
    
    /**
     * 检查字符串是否包含英文字符或数字
     * @param text 要检查的文本
     * @return 如果包含英文字符或数字返回true
     */
    public static boolean containsEnglish(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return ENGLISH_PATTERN.matcher(text).find();
    }
    
    /**
     * 检查字符串是否包含纯英文字符（不包括数字）
     * @param text 要检查的文本
     * @return 如果包含纯英文字符返回true
     */
    public static boolean containsPureEnglish(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return PURE_ENGLISH_PATTERN.matcher(text).find();
    }
    
    /**
     * 检查字符串是否只包含中文字符
     * @param text 要检查的文本
     * @return 如果只包含中文字符返回true
     */
    public static boolean isOnlyChinese(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.chars().allMatch(c -> CHINESE_PATTERN.matcher(String.valueOf((char) c)).matches());
    }
    
    /**
     * 检查字符串是否只包含英文字符和数字
     * @param text 要检查的文本
     * @return 如果只包含英文字符和数字返回true
     */
    public static boolean isOnlyEnglish(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.chars().allMatch(c -> ENGLISH_PATTERN.matcher(String.valueOf((char) c)).matches());
    }
    
    /**
     * 检查字符串是否只包含纯英文字符（不包括数字和特殊字符）
     * @param text 要检查的文本
     * @return 如果只包含纯英文字符返回true
     */
    public static boolean isOnlyPureEnglish(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.chars().allMatch(c -> PURE_ENGLISH_PATTERN.matcher(String.valueOf((char) c)).matches());
    }
    
    /**
     * 获取字符串中中文字符的数量
     * @param text 要检查的文本
     * @return 中文字符的数量
     */
    public static long getChineseCharCount(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return text.chars().mapToObj(c -> String.valueOf((char) c))
                .filter(s -> CHINESE_PATTERN.matcher(s).matches())
                .count();
    }
    
    /**
     * 获取字符串中英文字符的数量
     * @param text 要检查的文本
     * @return 英文字符的数量
     */
    public static long getEnglishCharCount(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return text.chars().mapToObj(c -> String.valueOf((char) c))
                .filter(s -> ENGLISH_PATTERN.matcher(s).matches())
                .count();
    }
}