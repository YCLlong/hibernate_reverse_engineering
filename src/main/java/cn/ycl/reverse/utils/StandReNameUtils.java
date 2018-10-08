package cn.ycl.reverse.utils;

public class StandReNameUtils {
    public static String reName(String name){
        StringBuilder sb = new StringBuilder(name.toLowerCase());
        while (sb.indexOf("_") != -1){
            int i = sb.indexOf("_");
            if(i == 0){
                sb.replace(i, i + 1, "");
                continue;
            }
            if(i+1 < sb.length()) {
                String c = String.valueOf(sb.charAt(i + 1)).toUpperCase();
                sb.replace(i, i + 2, c);
            }else {
                return sb.toString().replace("_","");
            }
        }
        return  sb.toString();
    }

    public static String classReName(String name){
        StringBuilder sb = new StringBuilder(reName(name));
        sb.replace(0,1,String.valueOf(sb.charAt(0)).toUpperCase());
        if(String.valueOf(sb.charAt(0)).equals("V")){
            sb.replace(1,2,String.valueOf(sb.charAt(1)).toUpperCase());
        }
        return sb.toString();
    }
}
