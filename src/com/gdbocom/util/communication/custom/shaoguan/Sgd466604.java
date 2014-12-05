package com.gdbocom.util.communication.custom.shaoguan;

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
 * 福利彩票485404交易的报文配置类
 * @author qm
 *
 */
public class Sgd466604 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"CLI_CODE",  "%-18s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }


    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

    	Map responseData = parseSequenceResponseBody(response);
     	//定义循环字段前标题的偏移量
         int loopOffset = 4;
//         if(responseData.containsKey("RECNUM")){
//         	
//         	loopOffset = Integer.parseInt(responseData.get("RECNUM").toString().trim());
//         }
         System.out.println("接拆完毕的整体报文字段：555555\n"+ loopOffset);
         System.out.println("\n"+ new String(response,"GBK"));
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
                {"RECNUM", "4", FieldTypes.STATIC},
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
    			{"CLI_CODE", "18",FieldTypes.STATIC},
                {"CLI_NAME", "40",FieldTypes.STATIC},
                {"BUSCLA", "2",FieldTypes.STATIC},
                {"AREACOD", "2",FieldTypes.STATIC},
                {"CLI_UNIT_CODE", "6",FieldTypes.STATIC},
                {"CLI_TRADE_FLAG", "2",FieldTypes.STATIC},
                {"PZFLG", "3",FieldTypes.STATIC},
                {"CLI_BANK_ACCOUNT", "40",FieldTypes.STATIC},
                {"CLI_TRADE_IDENT", "15",FieldTypes.STATIC},
                {"CLI_CODE_STATUS", "6",FieldTypes.STATIC},
        };
        int oneLoopLength =134;
        return Transation.unpacketTdLoop(loopOffset, response, oneLoopLength,format);
        
    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("FeCod", "466604");
        request.put("TTxnCd", "466604");
        request.put("CLI_CODE", "100000201410200012");
        Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466604);
        System.out.println(responseMap);
//        List loopBody = (List)responseMap.get("LoopBody");
//        String keyOrder[] = new String[]{"CODE"};
//    	//设置需要显示的值的类型
//    	Map keyType = new HashMap();
//    	//设置循环体需要显示的值和名称,
//    	Map loopShowKey = new HashMap();
//    	loopShowKey.put("CODE", "地域");
//    	keyType.put("BetLin", WelFormatter.getSingleton(WelFormatter.BETNUM));
//        for(int recordIndex=0; recordIndex<loopBody.size(); recordIndex++){
//        
//        		Map oneRecord = (Map)loopBody.get(recordIndex);
//        		for(int keyIndex=0; keyIndex<1; keyIndex++){
//        			
//        			//英文值，类似"DrawId"
//        			String key = keyOrder[keyIndex];
//        			//显示的中文名字，类似"投注期号"
//        			//String showName = (String)loopShowKey.get(key);
//        			//使用的格式化对象，类似 WelFormatter.getSingleton(WelFormatter.BETNUM)
//        			FormatterInterface type = (FormatterInterface)keyType.get(key);
//        			//为格式化的值
//        			String originValue = (String)oneRecord.get(key);
//        			System.out.println("<label>"+originValue.trim()+"</label><br/>");
//        		}
//        }
//        
        
    }
    
}
