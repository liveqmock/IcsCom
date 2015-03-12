package com.gdbocom.util.communication.custom.efek;

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
 * 南方电网460410交易的报文配置类
 * @author qm
 *
 */
public class Efek460410 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"SFFS", "%-3s", "110"},
        		{"JFH",  "%-20s", FieldSource.VAR},
        		{"DFNY", "%-6s", FieldSource.VAR},
        		{"CXFS", "%-1s",  FieldSource.VAR},
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
        
        //wasteLog.debug("接拆完毕的整体报文字段：\n"+responseData);
        
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
                {"PKGCNT", "6", FieldTypes.STATIC},
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
        		{"FYLX", "3",  FieldTypes.STATIC},
        		{"DWBM", "8",  FieldTypes.STATIC},
        		{"JFH", "20",  FieldTypes.STATIC},
        		{"KKYHDM", "4",  FieldTypes.STATIC},
        		{"BFJFBZ", "1",  FieldTypes.STATIC},
        		{"ZWLSH", "16",  FieldTypes.STATIC},
        		{"DFNY", "6",  FieldTypes.STATIC},
        		{"QFJE", "16",  FieldTypes.STATIC},
        		{"BJ", "16",  FieldTypes.STATIC},
        		{"WYJ", "16",  FieldTypes.STATIC},
        };
        int oneLoopLength = 106;
        return Transation.unpacketFixedOneLoop(loopOffset, oneLoopLength, response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "460410");
        request.put("FeCod", "460410");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("JFH", "0320009900178466");
        request.put("DFNY", "");//留空为查询全部
        //request.put("DFNY", "201412");
        request.put("CXFS", "0");//0：查询所有；1：单月欠费

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@EFEK"),//测试为33250端口
                (Map)request,
                TransationFactory.EFEK460410);
        System.out.println(responseMap);
        List loopBody = (List)responseMap.get("LoopBody");
        System.out.println(loopBody);
        for(int i=0; i<loopBody.size(); i++){
        	Map oneRecord = (Map)loopBody.get(i);
//            System.out.println("投注期号："+oneRecord.get("DrawId"));
//            System.out.println("投注号码："+oneRecord.get("BetLin"));
//            System.out.println("投注金额："+oneRecord.get("BetAmt"));
        }

    }
}
