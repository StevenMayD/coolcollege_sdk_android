package com.coolcollege.aar.global;


import com.coolcollege.aar.R;
import com.coolcollege.aar.application.Options;

/**
 * Created by Evan_for on 2020/7/29
 */
public class ErrorCode {

    private static final String CODE_1001 = "enterpriseapi.1001";
    private static final String CODE_1002 = "enterpriseapi.1002";
    private static final String CODE_2001 = "enterpriseapi.2001";
    private static final String CODE_E_4001 = "enterpriseapi.4001";
    private static final String CODE_P_4001 = "platformapi.4001";
    private static final String CODE_503001 = "enterpriseapi.503001";
    private static final String CODE_503003 = "enterpriseapi.503003";
    private static final String CODE_503004 = "enterpriseapi.503004";
    private static final String CODE_503005 = "enterpriseapi.503005";
    private static final String CODE_503006 = "enterpriseapi.503006";
    private static final String CODE_537001 = "platformapi.537001";
    private static final String CODE_538001 = "enterpriseapi.538001";
    private static final String CODE_503010 = "enterpriseapi.503010";
    private static final String CODE_560002 = "platformapi.560002";
    private static final String CODE_560004 = "enterpriseapi.560004";
    private static final String CODE_560005 = "enterpriseapi.560005";
    private static final String CODE_560007 = "enterpriseapi.560007";
    private static final String CODE_560008 = "enterpriseapi.560008";
    private static final String CODE_560009 = "enterpriseapi.560009";
    private static final String CODE_560010 = "enterpriseapi.560010";
    private static final String CODE_560011 = "enterpriseapi.560011";
    private static final String CODE_560012 = "enterpriseapi.560012";
    private static final String CODE_4003 = "enterpriseapi.4003";
    private static final String CODE_540001 = "platformapi.540001";
    private static final String CODE_540002 = "platformapi.540002";
    private static final String CODE_502012 = "enterpriseapi.502012";
    private static final String CODE_542008 = "enterpriseapi.502008";
    private static final String CODE_542007 = "enterpriseapi.502007";
    private static final String CODE_542009 = "enterpriseapi.502009";
    private static final String CODE_515001 = "enterpriseapi.515001";
    private static final String CODE_501004 = "enterpriseapi.501004";
    private static final String CODE_542001 = "enterpriseapi.542001";
    private static final String CODE_568006 = "enterpriseapi.568006";
    private static final String CODE_509002 = "enterpriseapi.509002";
    private static final String CODE_509003 = "enterpriseapi.509003";
    private static final String CODE_509005 = "enterpriseapi.509005";
    private static final String CODE_509006 = "enterpriseapi.509006";
    private static final String CODE_509007 = "enterpriseapi.509007";
    private static final String CODE_509008 = "enterpriseapi.509008";
    private static final String CODE_509009 = "enterpriseapi.509009";
    private static final String CODE_509010 = "enterpriseapi.509010";
    private static final String CODE_509011 = "enterpriseapi.509011";
    private static final String CODE_572001 = "enterpriseapi.572001";
    private static final String CODE_572002 = "enterpriseapi.572002";
    private static final String CODE_521009 = "platformapi.521009";
    private static final String CODE_521004 = "platformapi.521004";
    private static final String CODE_5001 = "platformapi.5001";
    public static final String CODE_521006 = "platformapi.521006";
    private static final String CODE_521008 = "platformapi.521008";
    private static final String CODE_521001 = "platformapi.521001";
    private static final String CODE_521002 = "platformapi.521002";
    private static final String CODE_521003 = "platformapi.521003";
    private static final String CODE_521013 = "platformapi.521013";
    private static final String CODE_521005 = "platformapi.521005";
    private static final String CODE_521007 = "platformapi.521007";
    private static final String CODE_521010 = "platformapi.521010";
    private static final String CODE_521011 = "platformapi.521011";
    private static final String CODE_509013 = "platformapi.509013";
    private static final String CODE_509014 = "platformapi.509014";
    private static final String CODE_P_509015 = "platformapi.509015";
    private static final String CODE_E_509015 = "enterpriseapi.509015";
    private static final String CODE_521012 = "platformapi.521012";
    private static final String CODE_521018 = "platformapi.521018";
    private static final String CODE_521019 = "platformapi.521019";
    private static final String CODE_521014 = "platformapi.521014";
    private static final String CODE_521015 = "platformapi.521015";
    private static final String CODE_521020 = "platformapi.521020";
    private static final String CODE_521021 = "platformapi.521021";
    private static final String CODE_521023 = "platformapi.521023";
    public static final String CODE_521026 = "platformapi.521026";
    public static final String CODE_521027 = "platformapi.521027";

    public static String getErrorMessage(String code) {
        String content;

        switch (code) {
            case CODE_1001:
                content = Options.get().getString(R.string.k_CODE_1001);
                break;
            case CODE_1002:
                content = Options.get().getString(R.string.k_CODE_1002);
                break;
            case CODE_2001:
                content = Options.get().getString(R.string.k_CODE_2001);
                break;
            case CODE_E_4001:
            case CODE_P_4001:
                content = Options.get().getString(R.string.k_CODE_P_4001);
                break;
            case CODE_4003:
                content = Options.get().getString(R.string.k_CODE_4003);
                break;
            case CODE_503001:
                content = Options.get().getString(R.string.k_CODE_503001);
                break;
            case CODE_503003:
                content = Options.get().getString(R.string.k_CODE_503003);
                break;
            case CODE_503004:
                content = Options.get().getString(R.string.k_CODE_503004);
                break;
            case CODE_503005:
                content = Options.get().getString(R.string.k_CODE_503005);
                break;
            case CODE_503006:
                content = Options.get().getString(R.string.k_CODE_503006);
                break;
            case CODE_537001:
                content = Options.get().getString(R.string.k_CODE_537001);
                break;
            case CODE_538001:
                content = Options.get().getString(R.string.k_CODE_538001);
                break;
            case CODE_503010:
                content = Options.get().getString(R.string.k_CODE_503010);
                break;
            case CODE_560002:
                content = Options.get().getString(R.string.k_CODE_560002);
                break;
            case CODE_560004:
                content = Options.get().getString(R.string.k_CODE_560004);
                break;
            case CODE_560005:
                content = Options.get().getString(R.string.k_CODE_560005);
                break;
            case CODE_560007:
                content = Options.get().getString(R.string.k_CODE_560007);
                break;
            case CODE_560008:
                content = Options.get().getString(R.string.k_CODE_560008);
                break;
            case CODE_560009:
                content = Options.get().getString(R.string.k_CODE_560009);
                break;
            case CODE_560010:
                content = Options.get().getString(R.string.k_CODE_560010);
                break;
            case CODE_560011:
                content = Options.get().getString(R.string.k_CODE_560011);
                break;
            case CODE_560012:
                content = Options.get().getString(R.string.k_CODE_560012);
                break;
            case CODE_540001:
                content = Options.get().getString(R.string.k_CODE_540001);
                break;
            case CODE_540002:
                content = Options.get().getString(R.string.k_CODE_540002);
                break;
            case CODE_502012:
                content = Options.get().getString(R.string.k_CODE_502012);
                break;
            case CODE_542008:
                content = Options.get().getString(R.string.k_CODE_542008);
                break;
            case CODE_542007:
                content = Options.get().getString(R.string.k_CODE_542007);
                break;
            case CODE_542009:
                content = Options.get().getString(R.string.k_CODE_542009);
                break;
            case CODE_515001:
                content = Options.get().getString(R.string.k_CODE_515001);
                break;
            case CODE_501004:
                content = Options.get().getString(R.string.k_CODE_501004);
                break;
            case CODE_542001:
                content = Options.get().getString(R.string.k_CODE_542001);
                break;
            case CODE_568006:
                content = Options.get().getString(R.string.k_CODE_568006);
                break;
            case CODE_509002:
                content = Options.get().getString(R.string.k_CODE_509002);
                break;
            case CODE_509003:
                content = Options.get().getString(R.string.k_CODE_509003);
                break;
            case CODE_509005:
                content = Options.get().getString(R.string.k_CODE_509005);
                break;
            case CODE_509006:
                content = Options.get().getString(R.string.k_CODE_509006);
                break;
            case CODE_509007:
                content = Options.get().getString(R.string.k_CODE_509007);
                break;
            case CODE_509008:
                content = Options.get().getString(R.string.k_CODE_509008);
                break;
            case CODE_509009:
                content = Options.get().getString(R.string.k_CODE_509009);
                break;
            case CODE_509011:
                content = Options.get().getString(R.string.k_CODE_509011);
                break;
            case CODE_509010:
            case CODE_521010:
                content = Options.get().getString(R.string.k_CODE_509010);
                break;
            case CODE_572001:
                content = Options.get().getString(R.string.k_CODE_572001);
                break;
            case CODE_572002:
                content = Options.get().getString(R.string.k_CODE_572002);
                break;
            case CODE_521009:
                content = Options.get().getString(R.string.k_CODE_521009);
                break;
            case CODE_521004:
                content = Options.get().getString(R.string.k_CODE_521004);
                break;
            case CODE_5001:
                content = Options.get().getString(R.string.k_CODE_5001);
                break;
            case CODE_521006:
                content = Options.get().getString(R.string.k_CODE_521006);
                break;
            case CODE_521008:
                content =Options.get().getString(R.string.k_CODE_521008);
                break;
            case CODE_521001:
                content = Options.get().getString(R.string.k_CODE_521001);
                break;
            case CODE_521002:
                content = Options.get().getString(R.string.k_CODE_521002);
                break;
            case CODE_521003:
                content = Options.get().getString(R.string.k_CODE_521003);
                break;
            case CODE_521005:
                content = Options.get().getString(R.string.k_CODE_521005);
                break;
            case CODE_521007:
                content = Options.get().getString(R.string.k_CODE_521007);
                break;
            case CODE_521011:
                content = Options.get().getString(R.string.k_CODE_521011);
                break;
            case CODE_509013:
                content = Options.get().getString(R.string.k_CODE_509013);
                break;
            case CODE_509014:
                content = Options.get().getString(R.string.k_CODE_509014);
                break;
            case CODE_P_509015:
            case CODE_E_509015:
            case CODE_521012:
                content = Options.get().getString(R.string.k_CODE_521012);
                break;
            case CODE_521018:
                content = Options.get().getString(R.string.k_CODE_521018);
                break;
            case CODE_521019:
                content = Options.get().getString(R.string.k_CODE_521019);
                break;
            case CODE_521014:
                content =Options.get().getString(R.string.k_CODE_521014);
                break;
            case CODE_521015:
                content = Options.get().getString(R.string.k_CODE_521015);
                break;
            case CODE_521013:
                content = Options.get().getString(R.string.k_CODE_521013);
                break;
            case CODE_521020:
                content = Options.get().getString(R.string.k_CODE_521020);
                break;
            case CODE_521021:
                content = Options.get().getString(R.string.k_CODE_521021);
                break;
            case CODE_521023:
                content = Options.get().getString(R.string.k_CODE_521023);
                break;
            case CODE_521026:
                content = Options.get().getString(R.string.k_CODE_521026);
                break;
            case CODE_521027:
                content = Options.get().getString(R.string.k_CODE_521027);
                break;
            default:
                content = Options.get().getString(R.string.k_other_error);
                break;
        }
        return content;
    }

    public static int getErrorCode(String stCode) {
        String[] split = stCode.split(".");
        if (split.length > 1) {
            String code = split[1];
            try {
                return Integer.parseInt(code);
            } catch (NumberFormatException e) {
                return -1;
            }
        } else {
            return -1;
        }
    }

}
