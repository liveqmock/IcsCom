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
//import com.gdbocom.util.format.FormatterInterface;
//import com.gdbocom.util.format.WelFormatter;

/**
 * 福利彩票485404交易的报文配置类
 * @author qm
 *
 */
public class Sgd466611 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"TXNCNL", "%-1s", "6"},
        		{"BUSTYPE",  "%-2s", FieldSource.VAR},
        		{"COMPNO",  "%-6s", FieldSource.VAR},
        		{"PAYFLG",  "%-18s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"CPROCOD","2",FieldTypes.STATIC},               
                {"PYEBNK", "6", FieldTypes.STATIC},  //客户身份证
                {"PYEACT", "21", FieldTypes.STATIC},           //客户姓名
                {"TXNAMT", "18", FieldTypes.STATIC},            //性别
                {"BUSSTS","2", FieldTypes.STATIC},              //年龄
                {"MACTXT", "2500", FieldTypes.STATIC},           //客户级别
                {"REMARK", "40", FieldTypes.STATIC},          //客户状态
                {"PAYBNK", "6", FieldTypes.STATIC},   //家庭住址
                {"LOGNO","14",FieldTypes.STATIC},   //家庭电话
                
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "466611");
        request.put("FeCod", "466611");
//        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("BUSTYPE", "61");
        request.put("COMPNO", "600000");
        request.put("PAYFLG", "16253736");
        Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466611);
        
        
        if("E".equals(responseMap.get("MsgTyp"))){
        	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
        }else{
        	
        	System.out.println(responseMap+"1111=========");
//        	  String keyOrder[] = new String[]{"CODE"};
//          	//设置需要显示的值的类型
//          	Map keyType = new HashMap();
//          	//设置循环体需要显示的值和名称,
//          	Map loopShowKey = new HashMap();
//          	loopShowKey.put("CODE", "地域");
//          	keyType.put("BetLin", WelFormatter.getSingleton(WelFormatter.BETNUM));
//              for(int recordIndex=0; recordIndex<loopBody.size(); recordIndex++){
//              
//              		Map oneRecord = (Map)loopBody.get(recordIndex);
//              		for(int keyIndex=0; keyIndex<1; keyIndex++){
//              			
//              			//英文值，类似"DrawId"
//              			String key = keyOrder[keyIndex];
//              			//显示的中文名字，类似"投注期号"
//              			//String showName = (String)loopShowKey.get(key);
//              			//使用的格式化对象，类似 WelFormatter.getSingleton(WelFormatter.BETNUM)
//              			FormatterInterface type = (FormatterInterface)keyType.get(key);
//              			//为格式化的值
//              			String originValue = (String)oneRecord.get(key);
//              			System.out.println("<label>"+originValue.trim()+"</label><br/>");
//              		}
//              }
        	 System.out.println(responseMap.get("LotNam"));
        }
        

       
        /*System.out.println(responseMap.get("IdNo"));*/

    }
}
