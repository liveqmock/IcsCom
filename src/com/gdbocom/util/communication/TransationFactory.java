package com.gdbocom.util.communication;

import com.gdbocom.util.communication.custom.gds.*;
import com.gdbocom.util.communication.custom.wel.*;
import com.gdbocom.util.communication.custom.shaoguan.*;
import com.gdbocom.util.communication.custom.efek.*;

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
    /** WEL应用的485407交易的代号 */
    public static final int WEL485407 = 7;
    /** WEL应用的485409交易的代号 */
    public static final int WEL485409 = 9;
    /** WEL应用的485410交易的代号 */
    public static final int WEL485410 = 10;
    /** WEL应用的485412交易的代号 */
    public static final int WEL485412 = 12;
    /** WEL应用的485413交易的代号 */
    public static final int WEL485413 = 13;
    /** WEL应用的485414交易的代号 */
    public static final int WEL485414 = 14;
    /** SGD应用的466601交易的代号 */
    public static final int SGD466601 = 15;
    /** SGD应用的466602交易的代号 */
    public static final int SGD466602 = 16;
    /** SGD应用的466603交易的代号 */
    public static final int SGD466603 = 17;
    /** SGD应用的466604交易的代号 */
    public static final int SGD466604 = 18;
    /** SGD应用的466605交易的代号 */
    public static final int SGD466605 = 19;
    /** SGD应用的466607交易的代号 */
    public static final int SGD466607 = 21;
    /** SGD应用的466611交易的代号 */
    public static final int SGD466611 = 22;
    /** SGD应用的466612交易的代号 */
    public static final int SGD466612 = 23;
    /** SGD应用的466613交易的代号 */
    public static final int SGD466613 = 24;
    /** SGD应用的466670交易的代号 */
    public static final int SGD466670 = 25;
    /** SGD应用的466671交易的代号 */
    public static final int SGD466671 = 26;
    /** SGD应用的466672交易的代号 */
    public static final int SGD466672 = 27;
    /** SGD应用的466675交易的代号 */
    public static final int SGD466675 = 28;
    /** SGD应用的466675交易的代号 */
    public static final int SGD466676 = 29;
    /** EFEK应用的460410交易的代号 */
    public static final int EFEK460410 = 30;
    /** EFEK应用的460411交易的代号 */
    public static final int EFEK460411 = 31;
    /** EFEK应用的460443交易的代号 */
    public static final int EFEK460443 = 32;
    /** EFEK应用的460430交易的代号 */
    public static final int EFEK460430 = 33;

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
            case TransationFactory.WEL485407: return new Wel485407();
            case TransationFactory.WEL485409: return new Wel485409();
            case TransationFactory.WEL485410: return new Wel485410();
            case TransationFactory.WEL485412: return new Wel485412();
            case TransationFactory.WEL485413: return new Wel485413();
            case TransationFactory.WEL485414: return new Wel485414();
            case TransationFactory.SGD466601: return new Sgd466601();
            case TransationFactory.SGD466602: return new Sgd466602();
            case TransationFactory.SGD466603: return new Sgd466603();
            case TransationFactory.SGD466604: return new Sgd466604();
            case TransationFactory.SGD466605: return new Sgd466605();
            case TransationFactory.SGD466607: return new Sgd466607();
            case TransationFactory.SGD466611: return new Sgd466611();
            case TransationFactory.SGD466612: return new Sgd466612();
            case TransationFactory.SGD466613: return new Sgd466613();
            case TransationFactory.SGD466670: return new Sgd466670();
            case TransationFactory.SGD466671: return new Sgd466671();
            case TransationFactory.SGD466672: return new Sgd466672();
            case TransationFactory.SGD466675: return new Sgd466675();
            case TransationFactory.SGD466676: return new Sgd466676();
            case TransationFactory.EFEK460410: return new Efek460410();
            case TransationFactory.EFEK460411: return new Efek460411();
            case TransationFactory.EFEK460443: return new Efek460443();
            case TransationFactory.EFEK460430: return new Efek460430();
            default: throw new IllegalArgumentException();
        }
    }
    
    
}
