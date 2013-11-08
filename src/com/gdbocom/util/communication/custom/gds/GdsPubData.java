package com.gdbocom.util.communication.custom.gds;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.Transation;

public class GdsPubData extends Transation {

    /* 功能选择项 */
    public static String functionAdd    = "0";
    public static String functionUpdate = "1";
    public static String functionQuery  = "2";
    public static String functionDelete = "3";
    public static String functionBrow   = "4";

    /* 企业种类 */
    public static String businessOfWater       = "44101";
    public static String businessOfMobile      = "44103";
    public static String businessOfUnicom      = "44102";
    public static String businessOfTele        = "44104";
    public static String businessOfGas         = "44105";
    public static String businessOfElectricity = "44106";
    public static String businessOfProvTv      = "44107";
    public static String businessOfCityTv      = "44108";

    /* 固话类型 */
    public static String contactNone   = "0";
    public static String contactMobile = "1";
    public static String contactUnicom = "2";
    public static String contactFixTel = "3";
    public static String contactPHS    = "4";
    public static String contactTianyi = "5";

    /* 支付行号 */
    public static String bankNo   = "30158100019";


    protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {
        /*
         *  key:关键字, 
         *  String.format{String} eg:%6c ：输出格式, 
         *  [FieldSource|value]：来源或值, 
         */
        Object[][] format = {

                {"Func", "%-1s",  FieldSource.VAR}, //功能选择:function*
                {"GdsBId", "%-5s",  FieldSource.VAR}, //业务标识|企业种类：businessOf*
                {"ActNo", "%-21s",  FieldSource.VAR}, //签约账号,客户输入
                {"ActTyp", "%-1s",  "4"}, //账户性质,4:太平洋卡
                {"ActNm", "%-60s",  FieldSource.VAR}, //签约账户，469998交易
                {"VchTyp", "%-3s",  ""}, //凭证种类,太平洋卡时为空
                {"VchNo", "%-8s",  ""}, //凭证号码,太平洋卡时为空
                {"BokSeq", "%-5s",  ""}, //一本通序号,太平洋卡时为空
                {"BCusNo", "%-13s",  FieldSource.VAR}, //客户号，469998交易
                {"Pin", "%-20s",  FieldSource.VAR}, //密码
                {"PfaSub", "%-3s",  ""}, //财政代码,太平洋卡时为空
                {"BCusId", "%-30s", ""}, //单位编码,太平洋卡时为空
                {"IdType", "%-2s",  "15"}, //证件类型,15：居民身份证
                {"IdNo", "%-30s",  FieldSource.VAR}, //证件号码，469998交易
                {"TelTyp", "%-1s",  FieldSource.VAR}, //固话类型,contact*,
                {"TelNo", "%-30s",  FieldSource.VAR}, //固话号码,客户输入
                {"MobTyp", "%-1s",  FieldSource.VAR}, //移话类型,contact*
                {"MobTel", "%-30s",  FieldSource.VAR}, //移话号码
                {"EMail", "%-60s",  FieldSource.VAR}, //电子邮件,客户输入
                {"Addr", "%-120s",  FieldSource.VAR}, //地址,客户输入
                {"IExtFg", "%-1s",  "Y"}, //私有数据标识
        };
        return Transation.packetSequence(request, format);
    }

    /**
     * 由于没有统一的公共头回应，因此这个方法直接返回空集的Map对象
     * 
     */

    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        return new HashMap();
    }

    /**
     * 通过手机号码匹配运营商类型
     * @param telNum 手机号码
     * @return 运营商类型
     */
    public static String telNum2telType(String telNum){

        if(11!=telNum.length()){
            return GdsPubData.contactNone;
        }
        //移动：2G号段（GSM）：134、135、136、137、138、139、150、151、152、158、
        //159；3G号段（TD-SCDMA，G3)：157、187、188、147（上网本）.
        //联通：2G号段(GSM)：130、131、132、155、156；3G号段(WCDMA，沃-WO)：185、
        //186.
        //电信：2G号段(CDMA)：133、153；3G号段(CDMA2000，天翼)：180、189.
        if(telNum.matches("1((3[4-9])|47|(5[0-27-9])|(8[78]))\\d{8}")){//移动
            return GdsPubData.contactMobile;
        }else if(telNum.matches("1((3[0-2])|(5[56])|(8[56]))\\d{8}")){//联通
            return GdsPubData.contactUnicom;
        }else if(telNum.matches("1(([35]3)|(8[09]))\\d{8}")){//电信
            return GdsPubData.contactTianyi;
        }else{
            return GdsPubData.contactNone;
        }
    }

    /**
     * 返回能够签约的业务列表
     * @return
     */
    public static Map getSignBusiness(){
        Map business = new HashMap();
        //一期上线没有报备移动签约，先行注释
        //business.put(GdsPubData.businessOfMobile, "移动签约");
        business.put(GdsPubData.businessOfUnicom, "联通签约");
        //business.put(GdsPubData.businessOfTele, "电信签约");
        business.put(GdsPubData.businessOfProvTv, "省有线电视签约");
        business.put(GdsPubData.businessOfCityTv,
                "市有线电视（珠江数码）签约");
        //business.put(GdsPubData.businessOfGas, "燃气签约");
        business.put(GdsPubData.businessOfWater, "水费签约");
        //排除电费划扣
        //business.put(GdsPubData.businessOfElectricity, "电费签约");
        return business;
    }

    /**
     * 返回企业代码列表
     * @return
     */
    public static Map getBCusId(){
        /* 企业代码 */
        Map bCusId = new HashMap();
        /* 初始化企业代码 */
        bCusId.put(GdsPubData.businessOfWater       , "190426853");
        bCusId.put(GdsPubData.businessOfMobile      , "618652334");
        bCusId.put(GdsPubData.businessOfUnicom      , "890346651");
        bCusId.put(GdsPubData.businessOfTele        , "0");
        bCusId.put(GdsPubData.businessOfGas         , "0");
        bCusId.put(GdsPubData.businessOfElectricity , "0");
        bCusId.put(GdsPubData.businessOfProvTv      , "726482280");
        bCusId.put(GdsPubData.businessOfCityTv      , "190498531");

        return bCusId;
    }

    /**
     * 返回业务类型列表
     * @return
     */
    public static Map getTBusTp(){
        /* 业务类型 */
        Map tBusTp = new HashMap();
        /* 初始化业务类型 */
        tBusTp.put(GdsPubData.businessOfWater       , "00201");
        tBusTp.put(GdsPubData.businessOfMobile      , "00403");
        tBusTp.put(GdsPubData.businessOfUnicom      , "00500");
        tBusTp.put(GdsPubData.businessOfTele        , "0");
        tBusTp.put(GdsPubData.businessOfGas         , "0");
        tBusTp.put(GdsPubData.businessOfElectricity , "0");
        tBusTp.put(GdsPubData.businessOfProvTv      , "01000");
        tBusTp.put(GdsPubData.businessOfCityTv      , "91001");

        return tBusTp;
    }

}
