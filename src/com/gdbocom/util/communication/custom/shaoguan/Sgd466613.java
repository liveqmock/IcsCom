package com.gdbocom.util.communication.custom.shaoguan;

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
 * 韶关代收付466613交易的报文配置类
 * @author qm
 *
 */
public class Sgd466613 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"TXNCNL", "%-1s", FieldSource.VAR},
        		{"PAYTYPE",  "%-2s", FieldSource.VAR},
        		{"LOGNO",  "%-14s", FieldSource.VAR},
        		{"PAYFLG",  "%-18s", FieldSource.VAR},
        		{"PAYACT",  "%-21s", FieldSource.VAR},
        		{"BUSLEI",  "%-2s", FieldSource.VAR},
        		{"AREA",  "%-2s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
              
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "466613");
        request.put("FeCod", "466613");
//        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("PAYTYPE", "02");
        request.put("LOGNO", "14112875100014");
        request.put("PAYFLG", "16253736");
        request.put("PAYACT", "6222620710003025622");
        request.put("BUSLEI", "04");
        request.put("AREA", "00");
        Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466613);
        
        
        if("E".equals(responseMap.get("MsgTyp"))){
        	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
        }else{
        	
        	System.out.println(responseMap+"1111=========");
        	 System.out.println(responseMap.get("LotNam"));
        }

    }
}
