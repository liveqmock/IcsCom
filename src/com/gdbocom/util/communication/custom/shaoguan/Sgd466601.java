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
 * 韶关代收付466601交易的报文配置类
 * @author qm
 *
 */
public class Sgd466601 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		
        		{"TXNCNL", "%-1s", FieldSource.VAR},
        		{"FUNFLG", "%-1s", FieldSource.VAR},           //客户级别
                {"CLI_TYPE", "%-2s", FieldSource.VAR},          //客户状态
                {"CLI_IDENTITY_CARD", "%-18s", FieldSource.VAR},   //家庭住址
                {"CLI_NAME","%-40s",FieldSource.VAR},   //家庭电话
                {"CLI_SEX", "%-1s", FieldSource.VAR},       //家庭邮编
                {"CLI_AGE", "%-3s", FieldSource.VAR},          //电子信箱
                {"CLI_LEVEL", "%-1s", FieldSource.VAR},          //电子信箱
                {"CLI_STATUS", "%-1s", FieldSource.VAR},          //电子信箱
                {"CLI_HOME_ADDRESS", "%-40s", FieldSource.VAR},          //电子信箱
                {"CLI_HOME_TELEPHONE", "%-15s", FieldSource.VAR},          //电子信箱
                {"CLI_HOME_POST", "%-6s", FieldSource.VAR},          //电子信箱
                {"CLI_EMAIL", "%-40s", FieldSource.VAR},          //电子信箱
                {"CLI_HOMEPAGE", "%-40s", FieldSource.VAR},       //主页
                {"CLI_UNIT_NAME","%-40s",FieldSource.VAR},        //单位名称
                {"CLI_UNIT_ADRESS", "%-40s", FieldSource.VAR},    //单位地址
                {"CLI_UNIT_TELEPHONE", "%-15s", FieldSource.VAR}, //单位电话
                {"CLI_UNIT_POST", "%-6s", FieldSource.VAR},       //单位邮编
                {"CLI_MARROW_UNIT_NAME","%-40s",FieldSource.VAR}, //配偶姓名
                {"CLI_MARROW_NAME", "%-40s", FieldSource.VAR},    //配偶单位 名称
                {"CLI_MARROW_UNIT_ADDRESS", "%-40s", FieldSource.VAR},//配偶地址
                {"CLI_MARROW_UNIT_TELEPHONE", "%-15s", FieldSource.VAR},//配偶电话
                {"CLI_MARROW_UNIT_POST", "%-6s", FieldSource.VAR},    //配偶邮编
                {"CLI_MENO", "%-256s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"DATATMP","4",FieldTypes.STATIC},               
                {"CLI_CODE", "18", FieldTypes.STATIC},  //客户身份证
                {"CLI_TYPE", "2", FieldTypes.STATIC},  //客户身份证
                {"CLI_IDENTITY_CARD", "18", FieldTypes.STATIC},  //客户身份证
                {"CLI_NAME", "40", FieldTypes.STATIC},           //客户姓名
                {"CLI_SEX", "1", FieldTypes.STATIC},            //性别
                {"CLI_AGE","3", FieldTypes.STATIC},              //年龄
                {"CLI_LEVEL", "1", FieldTypes.STATIC},           //客户级别
                {"CLI_STATUS", "1", FieldTypes.STATIC},          //客户状态
                {"CLI_HOME_ADDRESS", "40", FieldTypes.STATIC},   //家庭住址
                {"CLI_HOME_TELEPHONE","15",FieldTypes.STATIC},   //家庭电话
                {"CLI_HOME_POST", "6", FieldTypes.STATIC},       //家庭邮编
                {"CLI_EMAIL", "40", FieldTypes.STATIC},          //电子信箱
                {"CLI_HOMEPAGE", "40", FieldTypes.STATIC},       //主页
                {"CLI_UNIT_NAME","40",FieldTypes.STATIC},        //单位名称
                {"CLI_UNIT_ADRESS", "40", FieldTypes.STATIC},    //单位地址
                {"CLI_UNIT_TELEPHONE", "15", FieldTypes.STATIC}, //单位电话
                {"CLI_UNIT_POST", "6", FieldTypes.STATIC},       //单位邮编
                {"CLI_MARROW_UNIT_NAME","40",FieldTypes.STATIC}, //配偶姓名
                {"CLI_MARROW_NAME", "40", FieldTypes.STATIC},    //配偶单位 名称
                {"CLI_MARROW_UNIT_ADDRESS", "40", FieldTypes.STATIC},//配偶地址
                {"CLI_MARROW_UNIT_TELEPHONE", "15", FieldTypes.STATIC},//配偶电话
                {"CLI_MARROW_UNIT_POST", "6", FieldTypes.STATIC},    //配偶邮编
                {"CLI_MENO", "254", FieldTypes.STATIC},
                {"LOGNO", "14", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
    	 Map request = new HashMap();
        
         request.put("TTxnCd", "466601");
         request.put("FeCod", "466601");
         request.put("FUNFLG", "0");
         request.put("CLI_TYPE", "01");
         request.put("CLI_IDENTITY_CARD", "3333444456645666");
         request.put("CLI_NAME", "地方官");
         request.put("CLI_SEX", "1");
         request.put("CLI_AGE", "24");
         Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466601);
         
         
         if("E".equals(responseMap.get("MsgTyp"))){
         }else{
         	
         }
         

        
         /*System.out.println(responseMap.get("IdNo"));*/

    }
}
