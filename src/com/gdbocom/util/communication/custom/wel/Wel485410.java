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
 * 福利彩票485410交易的报文配置类
 * @author qm
 *
 */

public class Wel485410 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"PlanNo",  "%-20s", FieldSource.VAR},
        		{"CrdNo",  "%-30s",  FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485410");
        request.put("FeCod", "485410");
        //request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("CrdNo", "6222600710007815865");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_A"),
                (Map)request,
                TransationFactory.WEL485410);
        System.out.println(responseMap);

    }
}
