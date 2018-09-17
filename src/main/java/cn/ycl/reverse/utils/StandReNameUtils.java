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

    public static void main(String[] args) {
        String s = "_TB_NAME_HAHA_";
        System.out.println(reName(s));
    }
}
