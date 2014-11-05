package com.gdbocom.util.communication.custom.gds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.LoopResponseBody;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

public class Wel485414 extends Transation
	implements LoopResponseBody{

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"CrdNo", "%-21", FieldSource.VAR},
                {"BetTyp", "%-1", "0"},
                {"BegDat", "%-8", FieldSource.VAR},
                {"EndDat", "%-8", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Map responseData = parseSequenceResponseBody(response);
    	//定义循环字段前标题的偏移量
        int loopOffset = 0;
        if(responseData.containsKey("OFFSET")){
        	loopOffset = ((Integer)responseData.get("OFFSET")).intValue();
        }

        responseData.putAll(
        		this.parseLoopResponseBody(response, loopOffset)
        		);
        
        wasteLog.Write("接拆完毕的整体报文字段：\n"+responseData);
        
        return responseData;


    }

    protected List parseLoopResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TLogNo", "15",  FieldTypes.STATIC},
                {"DrawId", "5",  FieldTypes.STATIC},
                {"KenoId", "5", FieldTypes.STATIC},
                {"BetMul", "3",  FieldTypes.STATIC},
                {"BetLin", "128",  FieldTypes.STATIC},
                {"BetMod", "5",  FieldTypes.STATIC},
                {"PrzAmt", "15", FieldTypes.STATIC},
        };

        int _reponseHeadLength = 114;
        int _sequenceBodyLength = 176;
        return Transation.unpacketLoop(_reponseHeadLength+_sequenceBodyLength, 3, response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485413");
        request.put("FeCod", "485413");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("CrdNo", "6222600730002372142");
        request.put("BetTyp", "0");
        request.put("BegDat", "20141009");
        request.put("EndDat", "20141009");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_B"),
                (Map)request,
                TransationFactory.WEL485413);
//        System.out.println(responseMap.get("TCusId"));
//        System.out.println(responseMap.get("IdNo"));

    }


	public Map parseLoopResponseBody(byte[] response, int loopOffset)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}
}
