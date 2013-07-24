package com.gdbocom.util.communication;

import com.gdbocom.util.communication.custom.gds.*;

/**
 * Transation的工厂类，创建不同交易和交易码的拼包与解包Transation类，不同的
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
            default: return null;
        }
    }
}
