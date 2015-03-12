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
                {"DWBM", "8", FieldTypes.STATIC},
                {"JFH", "20", FieldTypes.STATIC},
                {"JSHMC", "64", FieldTypes.STATIC},
                {"YDDZ", "128", FieldTypes.STATIC},
                {"QYZT", "1", FieldTypes.STATIC},
                {"YQYYHDM", "4", FieldTypes.STATIC},
                {"YQYZH", "32", FieldTypes.STATIC},
                {"YQYZHMC", "128", FieldTypes.STATIC},
                {"YJYRQ", "8", FieldTypes.STATIC},
                {"YJYSJ", "6", FieldTypes.STATIC},
                {"BZ", "128", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "460443");
        request.put("FeCod", "460443");
        request.put("TxnSrc", "MB441");
        //报文体字段
		request.put("JFH", "0320009900178466");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@EFEK"),
                request,
                TransationFactory.EFEK460443);
        System.out.println(responseMap);
        /*System.out.println(responseMap.get("IdNo"));*/

    }

}
