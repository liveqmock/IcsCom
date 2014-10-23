package com.gdbocom.util.communication.custom.gds;

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

public class Wel485405 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"CusNam", "%-60s", FieldSource.VAR},
        		{"CrdNo",  "%-30s", FieldSource.VAR},
        		{"ActNo",  "%-21s", FieldSource.VAR},
        		{"NodNo",  "%-6s",  ""},
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
        request.put("TTxnCd", "469998");
        request.put("FeCod", "469998");
        //request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("ActNo", "6222600710007815865");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@GDS"),
                (Map)request,
                TransationFactory.GDS469998);
        System.out.println(responseMap.get("TCusId"));
        System.out.println(responseMap.get("IdNo"));

    }
}
