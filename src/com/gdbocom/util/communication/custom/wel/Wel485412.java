package com.gdbocom.util.communication.custom.wel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

/**
 * 福利彩票485412交易的报文配置类
 * @author qm
 *
 */
public class Wel485412 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"BetTyp", "%-1", "0"},
                {"SchTyp1", "%-1", "1"},
                {"SecLev", "%-1", "1"},
                {"CrdNo",  "%-21", FieldSource.VAR},
                {"GameId", "%-2", FieldSource.VAR},
                {"PlayId", "%-5", FieldSource.VAR},
                {"DrawId", "%-5", ""},
                {"KenoId", "%-5", ""},
                {"BetMod", "%-5", FieldSource.VAR},
                {"BetMet", "%-5", "1"},
                {"BetMul", "%-3", FieldSource.VAR},
                {"BetAmt", "%-15", FieldSource.VAR},
                {"GrpNum", "%-2", FieldSource.VAR},
                {"BetNum", "%-2", FieldSource.VAR},
                {"BetLin", "%-128", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
                {"TLogNo", "15", FieldTypes.STATIC},
                {"Cipher", "30", FieldTypes.STATIC},
                {"Verify", "30", FieldTypes.STATIC},
                {"LotNam", "30", FieldTypes.STATIC},
                {"LotBal", "18", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485412");
        request.put("FeCod", "485412");
        //request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("ActNo", "6222600710007815865");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_B"),
                (Map)request,
                TransationFactory.GDS469998);
        System.out.println(responseMap.get("TCusId"));
        System.out.println(responseMap.get("IdNo"));

    }
}
