package com.gdbocom.util.communication.custom.wel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

/**
 * 福利彩票业务485413交易的报文配置类
 * @author qm
 *
 */
public class Wel485413 extends Transation{


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
                {"GameId", "2",  FieldTypes.STATIC},
                {"PlayId", "5",  FieldTypes.STATIC},
                {"TLogNo", "30", FieldTypes.STATIC},
                {"DrawId", "5",  FieldTypes.STATIC},
                {"KenoId", "5",  FieldTypes.STATIC},
                {"BetMul", "3",  FieldTypes.STATIC},
                {"BetAmt", "15", FieldTypes.STATIC},
                {"BetLin", "128",FieldTypes.STATIC},
        };
        int headLenLength = 3;
        return Transation.unpacketLoop(loopOffset, headLenLength, response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485413");
        request.put("FeCod", "485413");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("GameId", "5");
        request.put("CrdNo", "60142890710180319");
        request.put("BetTyp", "0");
        request.put("BegDat", "20141201");
        request.put("EndDat", "20141231");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_B"),
                (Map)request,
                TransationFactory.WEL485413);
        System.out.println(responseMap);
        List loopBody = (List)responseMap.get("LoopBody");
        System.out.println(loopBody);
        for(int i=0; i<loopBody.size(); i++){
        	Map oneRecord = (Map)loopBody.get(i);
            System.out.println("投注期号："+oneRecord.get("DrawId"));
            System.out.println("投注号码："+oneRecord.get("BetLin"));
            System.out.println("投注金额："+oneRecord.get("BetAmt"));
        }

    }



}
