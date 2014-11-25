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
 * 福利彩票485404交易的报文配置类
 * @author qm
 *
 */
public class Wel485404 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"CusNam", "%-60s", FieldSource.VAR},
        		{"CrdNo",  "%-30s", FieldSource.VAR},
        		{"ActNo",  "%-21s", FieldSource.VAR},
        		{"NodNo",  "%-6s",  FieldSource.PROPS},
        		{"IdTyp",  "%-2s",  FieldSource.VAR},
        		{"IdNo",   "%-30s", FieldSource.VAR},
        		{"MobTel", "%-15s", FieldSource.VAR},
        		{"FixTel", "%-20s", ""},
        		{"Email",  "%-30s", ""},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
                {"LotNam", "30", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485404");
        request.put("FeCod", "485404");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("CusNam", "BANKCOMM");
        request.put("CrdNo", "6222600710007815865");
        request.put("ActNo", "6222600710007815865");
        request.put("IdTyp", "15");
        request.put("IdNo", "44010419850301501X");
        request.put("MobTel", "13570959854");
        request.put("FixTel", "");
        request.put("Email", "");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_A"),
                request,
                TransationFactory.WEL485404);
        System.out.println(responseMap);
        /*System.out.println(responseMap.get("IdNo"));*/

    }
}
