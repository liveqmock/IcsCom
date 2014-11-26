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
 * 福利彩票业务485409交易的报文配置类
 * @author qm
 *
 */
public class Wel485409 extends Transation{


	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"CrdNo", "%-30", FieldSource.VAR},
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
                {"TmpTot", "3", FieldTypes.STATIC},
                {"TotCnt", "8", FieldTypes.STATIC},
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
                {"PlanNo", "20",  FieldTypes.STATIC},
                {"PlanNm", "30",  FieldTypes.STATIC},
                {"MobTel", "15",  FieldTypes.STATIC},
                {"BetPer", "5",  FieldTypes.STATIC},
                {"BetLin", "128",  FieldTypes.STATIC},
                {"BetAmt", "15",  FieldTypes.STATIC},
                {"DoPer",  "5",  FieldTypes.STATIC},
                {"LevPer", "5",  FieldTypes.STATIC},
                {"Status", "1",  FieldTypes.STATIC},
        };
        int headLenLength = 3;
        return Transation.unpacketLoop(loopOffset, headLenLength, response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "485409");
        request.put("FeCod", "485409");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("CrdNo", "6222600710009621634");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_A"),
                (Map)request,
                TransationFactory.WEL485409);
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
