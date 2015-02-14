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
 * 福利彩票485414交易的报文配置类
 * @author qm
 *
 */
public class Wel485414 extends Transation{

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"GameId", "%-2", FieldSource.VAR},
                {"CrdNo", "%-21", FieldSource.VAR},
                {"BetTyp", "%-1", FieldSource.VAR},
                {"BegDat", "%-8", FieldSource.VAR},
                {"EndDat", "%-8", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {
System.out.println("response::"+new String(response, "GBK"));
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

    /**
     * 接拆返回报文里面的非循环部分
     * @param response 除ics报文头外，剩下的报文
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map parseSequenceResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat", "4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
                {"PageNo", "4", FieldTypes.STATIC},
                {"VarSize","1", FieldTypes.STATIC},
                {"Ttl",    "3", FieldTypes.VARIABLELENGTH},
                {"SubTtl", "3", FieldTypes.VARIABLELENGTH},
        };
        Map responseData = Transation.unpacketsSequence(response, format);
        
        return responseData;

    }

    /**
     * 接拆返回报文里面的循环部分
     * @param response 除ics报文头外，剩下的报文
     * @param loopOffset 除ics报文头外，非循环部分的长度
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map parseLoopResponseBody(byte[] response, int loopOffset)
            throws UnsupportedEncodingException {
        Object[][] format = {
                {"TmpNam", "3",  FieldTypes.STATIC},
                {"TLogNo", "30"  ,  FieldTypes.STATIC},
            	{"PlayId", "5"   ,  FieldTypes.STATIC},
            	{"DrawId", "5"   ,  FieldTypes.STATIC},
            	{"KenoId", "5"   ,  FieldTypes.STATIC},
            	{"BetMul", "3"   ,  FieldTypes.STATIC},
            	{"BetLin", "128" ,  FieldTypes.STATIC},
            	{"BetMod", "5"   ,  FieldTypes.STATIC},
            	{"PrzAmt", "15"  ,  FieldTypes.STATIC},
                {"DrawNm", "15"  ,  FieldTypes.STATIC},
        };
        int headLenLength = 3;
        return Transation.unpacketLoop(loopOffset, headLenLength, response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485414");
        request.put("FeCod", "485414");
        request.put("TxnSrc", "MB441");
        //报文体字段
        //request.put("GameId", "5");//双色球
        request.put("GameId", "7");//快乐十分
        //request.put("CrdNo", "60142890710180319");//双色球
        request.put("CrdNo", "6222600710009621634");//快乐十分
        request.put("BetTyp", "0");
        request.put("BegDat", "19000101");
        request.put("EndDat", "20991231");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_B"),
                (Map)request,
                TransationFactory.WEL485414);
        System.out.println(responseMap);

    }

}
