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
 * 南方电网460411交易的报文配置类
 * @author qm
 *
 */
public class Efek460411 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"SFFS", "%-3s", FieldSource.VAR},
        		{"FYLX", "%-3s", FieldSource.VAR},
        		{"DWBM", "%-8s", FieldSource.VAR},
        		{"JFH", "%-20s", FieldSource.VAR},
        		{"KKYHDM", "%-4s", FieldSource.VAR},
        		{"BFJFBZ", "%-1s", FieldSource.VAR},
        		{"ZWLSH", "%-16s", FieldSource.VAR},
        		{"DFNY", "%-6s", FieldSource.VAR},
        		{"QFJE", "%-16s", FieldSource.VAR},
        		{"BJ", "%-16s", FieldSource.VAR},
        		{"WYJ", "%-16s", FieldSource.VAR},
        		{"FKFS", "%-1s", FieldSource.VAR},
        		{"SKJE", "%-16s", FieldSource.VAR},
        		{"ActFlg", "%-1s", FieldSource.VAR},
        		{"AVchTp", "%-3s", FieldSource.VAR},
        		{"VchCod", "%-8s", FieldSource.VAR},
        		{"BokAct", "%-21s", FieldSource.VAR},
        		{"KKZHMC", "%-128s", FieldSource.VAR},
        		{"BActSq", "%-5s", FieldSource.VAR},
        		{"RvsNo", "%-12s", FieldSource.VAR},
        		{"VchTyp", "%-3s", FieldSource.VAR},
        		{"VchNo", "%-8s", FieldSource.VAR},
        		{"BilDat", "%-8s", FieldSource.VAR},
        		{"PinBlk", "%-20s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
                {"TckNo", "11", FieldTypes.STATIC},
                {"JYLSH", "24", FieldTypes.STATIC},
                {"JYRQ", "8", FieldTypes.STATIC},
                {"JYSJ", "6", FieldTypes.STATIC},
                {"YJYLSH", "24", FieldTypes.STATIC},
                {"YJYRQ", "8", FieldTypes.STATIC},
                {"YJYSJ", "6", FieldTypes.STATIC},
                {"JFH", "20", FieldTypes.STATIC},
                {"DFNY", "6", FieldTypes.STATIC},
                {"SKJE", "16", FieldTypes.STATIC},
                {"JFJGSM", "128", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "460411");
        request.put("FeCod", "460411");
        request.put("TxnSrc", "MB441");
        //报文体字段
		request.put("SFFS", "110");//
		request.put("FYLX", "010");
		request.put("DWBM", "032025");
		request.put("JFH", "0320009900178466");
		request.put("KKYHDM", "301");
		request.put("BFJFBZ", "1");
		request.put("ZWLSH", "1000000013185399");
		request.put("DFNY", "201502");
		request.put("QFJE", "76337");
		request.put("BJ", "76337");
		request.put("WYJ", "0");
		request.put("FKFS", "2");//2非现金
		request.put("SKJE", "76337");
		request.put("ActFlg", "4");//卡
		request.put("AVchTp", "");
		request.put("VchCod", "");
		request.put("BokAct", "60142890710180319");
		request.put("KKZHMC", "杨立文");
		request.put("BActSq", "");
		request.put("RvsNo", "");
		request.put("VchTyp", "");
		request.put("VchNo", "");
		request.put("BilDat", "");
		request.put("PinBlk", "12345678910");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@EFEK"),
                request,
                TransationFactory.EFEK460411);
        System.out.println(responseMap);
        /*System.out.println(responseMap.get("IdNo"));*/

    }

}
