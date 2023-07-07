package org.framework.core.util;

import java.util.Collection;

public class ValidationUtil {

    public static Boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static Boolean isBlank(String str) {
        return null == str || "".equals(str);
    }

}
