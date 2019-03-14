package com.zzz.smo.userservice.server.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Sean
 * @Date: 2019/1/13 22:38
 */
public class PermissionUtil {
    public static String list2String(List<String> list){
        return String.join(",",list);
    }

    public static List<String> string2List(String str){
        return Arrays.asList(str.split(",")).
                stream().map(s -> (s.trim())).collect(Collectors.toList());
    }
}
