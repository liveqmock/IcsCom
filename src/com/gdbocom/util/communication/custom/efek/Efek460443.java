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
 * 南方电网460443交易的报文配置类
 * @author qm
 *
 */
public class Efek460443 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"JFH", "%-20s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
                {"DWBM", "%-8s", FieldTypes.STATIC},
                {"JFH", "%-20s", FieldTypes.STATIC},
                {"JSHMC", "%-64s", FieldTypes.STATIC},
                {"YDDZ", "%-128s", FieldTypes.STATIC},
                {"QYZT", "%-1s", FieldTypes.STATIC},
                {"YQYYHDM", "%-4s", FieldTypes.STATIC},
                {"YQYZH", "%-32s", FieldTypes.STATIC},
                {"YQYZHMC", "%-128s", FieldTypes.STATIC},
                {"YJYRQ", "%-8s", FieldTypes.STATIC},
                {"YJYSJ", "%-6s", FieldTypes.STATIC},
                {"BZ", "%-128s", FieldTypes.STATIC},
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
		request.put("JFH", "");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@EFEK"),
                request,
                TransationFactory.EFEK460443);
        System.out.println(responseMap);
        /*System.out.println(responseMap.get("IdNo"));*/

    }

}
