package com.gdbocom.util.communication;

import com.gdbocom.util.communication.custom.gds.*;
import com.gdbocom.util.communication.custom.wel.Wel485404;
import com.gdbocom.util.communication.custom.wel.Wel485405;
import com.gdbocom.util.communication.custom.wel.Wel485412;
import com.gdbocom.util.communication.custom.wel.Wel485413;
import com.gdbocom.util.communication.custom.wel.Wel485414;

/**
 * Transation的简单工厂类，同时用于登记各个不同的Transation实现类，
 * 能够创建不同交易和交易码的拼包与解包Transation类，不同的
 * Transation类有自己的登记值。
 * @author qm
 *
 */
public class TransationFactory {

    /** LSHA应用的482150交易的代号 */
    public static final int LSHA482150 = 1;

    /** GDS应用的公共报文头代号 */
    public static final int GDSPubData = 2;
    /** GDS应用的469901交易的代号 */
    public static final int GDS469901 = 3;
    /** GDS应用的469998交易的代号 */
    public static final int GDS469998 = 4;
    /** WEL应用的485404交易的代号 */
    public static final int WEL485404 = 5;
    /** WEL应用的485405交易的代号 */
    public static final int WEL485405 = 6;
    /** WEL应用的485412交易的代号 */
    public static final int WEL485412 = 7;
    /** WEL应用的485413交易的代号 */
    public static final int WEL485413 = 8;
    /** WEL应用的485414交易的代号 */
    public static final int WEL485414 = 9;

    /**
     * 根据不同的交易代号，返回不同的拼解包类。
     * @param transationCode 代号
     * @return 拼解包类，如不存在返回null
     */
    public static Transation createTransation(int transationCode){
        switch(transationCode){
            case TransationFactory.GDSPubData: return new GdsPubData();
            case TransationFactory.GDS469901: return new Gds469901();
            case TransationFactory.GDS469998: return new Gds469998();
            case TransationFactory.WEL485404: return new Wel485404();
            case TransationFactory.WEL485405: return new Wel485405();
            case TransationFactory.WEL485412: return new Wel485412();
            case TransationFactory.WEL485413: return new Wel485413();
            case TransationFactory.WEL485414: return new Wel485414();
            default: return null;
        }
    }
}
