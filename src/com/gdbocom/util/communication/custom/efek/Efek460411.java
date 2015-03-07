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
                {"TckNo", "%-11s", FieldTypes.STATIC},
                {"JYLSH", "%-24s", FieldTypes.STATIC},
                {"JYRQ", "%-8s", FieldTypes.STATIC},
                {"JYSJ", "%-6s", FieldTypes.STATIC},
                {"YJYLSH", "%-24s", FieldTypes.STATIC},
                {"YJYRQ", "%-8s", FieldTypes.STATIC},
                {"YJYSJ", "%-6s", FieldTypes.STATIC},
                {"JFH", "%-20s", FieldTypes.STATIC},
                {"DFNY", "%-6s", FieldTypes.STATIC},
                {"SKJE", "%-16s", FieldTypes.STATIC},
                {"JFJGSM", "%-128s", FieldTypes.STATIC},
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
		request.put("SFFS", "");
		request.put("FYLX", "");
		request.put("DWBM", "");
		request.put("JFH", "");
		request.put("KKYHDM", "");
		request.put("BFJFBZ", "");
		request.put("ZWLSH", "");
		request.put("DFNY", "");
		request.put("QFJE", "");
		request.put("BJ", "");
		request.put("WYJ", "");
		request.put("FKFS", "");
		request.put("SKJE", "");
		request.put("ActFlg", "");
		request.put("AVchTp", "");
		request.put("VchCod", "");
		request.put("BokAct", "");
		request.put("KKZHMC", "");
		request.put("BActSq", "");
		request.put("RvsNo", "");
		request.put("VchTyp", "");
		request.put("VchNo", "");
		request.put("BilDat", "");
		request.put("PinBlk", "");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@EFEK"),
                request,
                TransationFactory.EFEK460411);
        System.out.println(responseMap);
        /*System.out.println(responseMap.get("IdNo"));*/

    }

}
