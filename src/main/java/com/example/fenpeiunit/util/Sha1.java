package com.example.fenpeiunit.util;

public class Sha1 {
    private static String hexcase = "0";  /* hex output format. 0 - lowercase; 1 - uppercase        */
    String b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
    private static int chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */


    /*
     * These are the functions you'll usually want to call
     * They take string arguments and return either hex or base-64 encoded strings
     */
    /*public String hex_sha1(String s){
        return binb2hex(core_sha1(str2binb(s),s.length() * chrsz));
    }*/

    public static int[] str2binb(String str) {
        int max_i = (str.length()-1) * chrsz;
        int[] bin = new int[(max_i>>5)+1];// max_i>>5  是最大index
        int mask = (1 << chrsz) - 1;
        for(int i = 0; i < str.length() * chrsz; i += chrsz){
            bin[i>>5] |= (str.charAt(i / chrsz) & mask) << (32 - chrsz - i%32);
        }
        return bin;
    }

    public static int rol(int num,int cnt){
        return (num << cnt) | (num >>> (32 - cnt));
    }

    public static int safe_add(int x,int y){
        int lsw = (x & 0xFFFF) + (y & 0xFFFF);
        int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return (msw << 16) | (lsw & 0xFFFF);
    }

    public static int sha1_ft(int t, int b, int c, int d){
        if(t < 20) return (b & c) | ((~b) & d);
        if(t < 40) return b ^ c ^ d;
        if(t < 60) return (b & c) | (b & d) | (c & d);
        return b ^ c ^ d;
    }

    public static int sha1_kt(int t){
        return (t < 20) ?  1518500249 : (t < 40) ?  1859775393 :
                (t < 60) ? -1894007588 : -899497514;
    }

    public static int[] core_sha1(int[] s,int len){
        /* append padding */
        int max_index = ((len + 64 >> 9) << 4) + 15;
        int[] x = new int[max_index + 1];

        //TODO s.length>max_index
        for(int i = 0; i < s.length; i++){
            x[i] = s[i];
        }

        x[len >> 5] |= 0x80 << (24 - len % 32);
        x[((len + 64 >> 9) << 4) + 15] = len;

        int[] w = new int[80];
        int a =  1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d =  271733878;
        int e = -1009589776;

        for(int i = 0; i < x.length; i += 16){
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            int olde = e;

            for(int j = 0; j < 80; j++){
                if(j < 16) w[j] = x[i + j];
                else w[j] = rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1);
                int t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)),
                        safe_add(safe_add(e, w[j]), sha1_kt(j)));
                e = d;
                d = c;
                c = rol(b, 30);
                b = a;
                a = t;
            }

            a = safe_add(a, olda);
            b = safe_add(b, oldb);
            c = safe_add(c, oldc);
            d = safe_add(d, oldd);
            e = safe_add(e, olde);
        }
        return new int[]{a, b, c, d, e};
    }

    public static String binb2hex(int[] binarray){
        //js  0 false
        String hex_tab = !"0".equals(hexcase) ? "0123456789ABCDEF" : "0123456789abcdef";
        String str = "";
        for(int i = 0; i < binarray.length * 4; i++){
            //user StringBuffer
            str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) + ""+
                    hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);
        }
        return str;
    }

    public static String hex_sha1(String str){
        return binb2hex(core_sha1(str2binb(str),str.length() * chrsz));
    }
}
