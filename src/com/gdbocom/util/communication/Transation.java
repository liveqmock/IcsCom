package com.gdbocom.util.communication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.jsp.PageContext;
import com.gdbocom.util.waste.WasteLog;

/**
 * 实现通讯报文与表单项之间的互相转换
 * 
 * @author qm
 * 
 */
public abstract class Transation {

    private static WasteLog wasteLog = new WasteLog("c:/gzLog_sj");

    /**
     * 添加报文头字段和报文体字段并发送
     * @param pageContext
     * @param txnCod 交易码
     * @param serverName
     * @param transationFactoryType 登记的交易类型
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
	public static Map createMapSend(PageContext pageContext,
			String txnCod,
			String serverName,
			int transationFactoryType) throws UnknownHostException, IOException {

		wasteLog.Write("进入"+Transation.class.getName());

        //配置发送参数
        Map requestSt = new HashMap();
        // 报文头字段
        requestSt.put("TTxnCd", txnCod);
        requestSt.put("FeCod", txnCod);

        // 报文体字段
		String key;
		for (Enumeration e = pageContext.getSession().getAttributeNames();
				e.hasMoreElements();){
			key = (String)e.nextElement();
			requestSt.put(key,
					pageContext.getAttribute(key, PageContext.SESSION_SCOPE));
		}

		return Transation.exchangeData(IcsServer.getServer(serverName),
                requestSt, transationFactoryType);
	}

    /**
     * 通讯报文适配器，将完成发送表单项与通讯报文间的转换、通讯功能。
     * 
     * @param server
     *            指定通讯的IcsServer类
     * @param request
     *            请求表单项
     * @param transationFactoryType
     *            TransationFactory类中登记的签约报文类型， eg:TransationFactory.GDS469901
     * @return 返回表单项，通过判断MsgTyp决定报文是否成功，N为成功，E为不成功报 文，其他为异常
     * @throws UnknownHostException
     * @throws IOException
     */
    public static Map exchangeData(IcsServer server, Map request,
            int transationFactoryType) throws UnknownHostException, IOException {

        // 实例化具体报文实现类
        Transation ts = TransationFactory
                .createTransation(transationFactoryType);
        // 通过表单项获取通讯报文
        byte[] requestPacket = ts.buildRequestPacket(request);
        wasteLog.Write("---------------------");
        wasteLog.Write("发送报文：" + new String(requestPacket, "GBK"));
        // 通讯
        byte[] responsePacket = server.send(requestPacket);
        wasteLog.Write("接收报文：" + new String(responsePacket, "GBK"));
        wasteLog.Write("---------------------");

        // 通过通讯返回报文得到Map值
        Map response = ts.parseResponseMap(responsePacket);
        return response;
    }

    /**
     * 生成通讯报文，通讯报文=报文头+报文体，报文头统一使用TIA头，报文体为自定义。
     * 
     * @param request
     *            表单项
     * @return 通讯报文
     * @throws UnsupportedEncodingException
     */
    private byte[] buildRequestPacket(Map request)
            throws UnsupportedEncodingException {

        byte[] requestHead = buildTiaHead(request);
        byte[] requestBody = buildRequestBody(request);

        return Transation.mergeByte(requestHead, requestBody);
    }

    /**
     * 生成TIA公共报文头
     * 
     * @param request
     *            表单项
     * @return TIA公共报文头
     * @throws UnsupportedEncodingException
     */
    private byte[] buildTiaHead(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = { { "CCSCod", "%-4s", "TLU6" },
                { "TTxnCd", "%-6s", FieldSource.VAR },
                { "FeCod", "%-6s", FieldSource.VAR },
                { "TrmNo", "%-7s", FieldSource.PROPS },
                { "TxnSrc", "%-5s", FieldSource.PROPS },
                { "NodTrc", "%-15s", "0" },
                { "TlrId", "%-7s", FieldSource.PROPS },
                { "TIATyp", "%-1s", "T" },
                { "AthLvl", "%-2s", "00" },
                { "Sup1Id", "%-7s", "" },
                { "Sup2Id", "%-7s", "" },
                { "Sup1Pw", "%-6s", "" },
                { "Sup2Pw", "%-6s", "" },
                { "Sup1Dv", "%-1s", "" },
                { "Sup2Dv", "%-1s", "" },
                { "AthTbl", "%-60s", "" },
                { "AthLog", "%-1s", "" },
                { "HLogNo", "%-9s", "" },
                { "CprInd", "%-1s", "0" },
                { "EnpInd", "%-1s", "0" },
                { "NodNo", "%-6s", FieldSource.PROPS },
                { "OprLvl", "%-1s", "" },
                { "TrmVer", "%-8s", "v0000001" },
                { "OutSys", "%-1s", "" },
                { "Fil", "%-2s", "" }
                };
        return Transation.packetSequence(request, format);

    }

    /**
     * 生成自定义请求报文体
     * 
     * @param request
     *            表单项
     * @return 报文体
     */
    protected abstract byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException;

    /**
     * 获取返回报文字段， 返回报文有正常报文与错误报文两种，他们具有同样的TOA报文头，通过判断返回字段 MsgTyp是否为N可知。
     * 
     * @param 返回报文
     * @return 返回Map值，MsgTyp不为N，即为错误报文
     * @throws UnsupportedEncodingException
     */
    private Map parseResponseMap(byte[] response)
            throws UnsupportedEncodingException {

        Map responseData = new HashMap();

        // 处理报文头
        Map toaData = parseToaHead(response);
        responseData.putAll(toaData);

        // 截取报文体
        int toaLength = 114;
        byte[] responseBody = new byte[response.length - toaLength];
        System.arraycopy(response, toaLength, responseBody, 0,
                responseBody.length);

        // 根据MsgTyp值对不同类型报文进行处理
        if (responseData.containsKey("MsgTyp")) {
            if ("N".equals(responseData.get("MsgTyp"))) {// ICS返回Normal
                Map bodyData = parseNormalResponseBody(responseBody);

                responseData.putAll(bodyData);
                return responseData;

            } else if ("E".equals(responseData.get("MsgTyp"))) {// ICS返回Error
                Map bodyData = parseErrorResponseBody(responseBody);

                responseData.putAll(bodyData);
                return responseData;

            }
        }
        // ICS异常返回
        updateMsgTyp(responseData, "E");

        return responseData;

    }

    /**
     * 解析TOA公共报文头
     * 
     * @param response
     *            返回报文
     * @return 报文头字段
     * @throws UnsupportedEncodingException
     */
    private Map parseToaHead(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = { { "Fil1", "3", FieldTypes.STATIC },
                { "MsgTyp", "1", FieldTypes.STATIC },
                { "RspCod", "6", FieldTypes.STATIC },
                { "ErrFld", "4", FieldTypes.STATIC },
                { "TrmNo", "7", FieldTypes.STATIC },
                { "TrmSqn", "6", FieldTypes.STATIC },
                { "STxnCd", "4", FieldTypes.STATIC },
                { "SAplCd", "2", FieldTypes.STATIC },
                { "TxnSym", "3", FieldTypes.STATIC },
                { "TxnDat", "8", FieldTypes.STATIC },
                { "TxnTm", "6", FieldTypes.STATIC },
                { "ActDat", "8", FieldTypes.STATIC },
                { "HLogNo", "9", FieldTypes.STATIC },
                { "TckNo", "11", FieldTypes.STATIC },
                { "PagId", "1", FieldTypes.STATIC },
                { "CprInd", "1", FieldTypes.STATIC },
                { "EnpInd", "1", FieldTypes.STATIC },
                { "NodNo", "6", FieldTypes.STATIC },
                { "AthLog", "1", FieldTypes.STATIC },
                { "FinFlg", "1", FieldTypes.STATIC },
                { "AthLvl", "2", FieldTypes.STATIC },
                { "Sup1Id", "7", FieldTypes.STATIC },
                { "Sup2Id", "7", FieldTypes.STATIC },
                { "Fil2", "5", FieldTypes.STATIC },
                { "DatLen", "4", FieldTypes.STATIC } };
        return Transation.unpacketsSequence(response, format);
    }

    /**
     * 解析自定义返回报文体
     * 
     * @return 报文体
     * @throws UnsupportedEncodingException
     */
    protected abstract Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException;

    /**
     * 解析错误返回报文体
     * 
     * @param response
     *            返回报文
     * @return 报文体字段
     * @throws UnsupportedEncodingException
     */
    private Map parseErrorResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = { { "TmpDat", "4", FieldTypes.STATIC },
                { "ApCode", "2", FieldTypes.STATIC },
                { "OFmtCd", "3", FieldTypes.STATIC },
                { "RspCod", "6", FieldTypes.STATIC },
                { "InPos", "4", FieldTypes.STATIC },
                { "RspMsg", "56", FieldTypes.STATIC } };
        return Transation.unpacketsSequence(response, format);
    }

    /**
     * 定长报文生成通用方法，同时将编码转换成ICS的GBK编码
     * 
     * @param request
     *            表单项
     * @param format
     *            表单格式<br/>
     *            各字段意思：<br/>
     *            key:关键字, String.format{String} eg:%6c ：输出格式,
     *            [FieldSource|value]：来源或值,
     * 
     * @throws UnsupportedEncodingException
     */
    protected static byte[] packetSequence(Map request, Object[][] format)
            throws UnsupportedEncodingException {

        StringBuffer buffer = new StringBuffer();
        String value = "";

        int fieldLength = 0;
        int fixlen = 0;
        for (int i = 0; i < format.length; i++) {

            fieldLength = Integer.parseInt(((String) format[i][1]).replaceAll(
                    "[^\\d]+", ""));

            if (String.class == format[i][2].getClass()) {// 固定值
                value = (String) format[i][2];

            } else if (FieldSource.VAR.equals(format[i][2])) {// 变量，从表单中获取
                value = (String) request.get((String) format[i][0]);

            } else if (FieldSource.PROPS.equals(format[i][2])) {// 变量，从配置文件获取
                value = ConfigProps.getInstance().getProperty(
                        (String) format[i][0]);

            } else {// 未定义类型扔出运行时错误
                throw new IllegalArgumentException();
            }
            fixlen = Integer.parseInt(((String) format[i][1]).replaceAll(
                    "[^-\\d]+", ""));
            value = (null == value ? fixFill("", " ", fixlen) : fixFill(value,
                    " ", fixlen));

            String tmp = new String(Transation.copyOf(value.getBytes("GBK"),
                    fieldLength), "GBK");

            buffer.append(tmp);

        }

        return buffer.toString().getBytes("GBK");

    }

    /**
     * 定长报文解析通用方法，同时将编码由ICS的GBK编码转换成Unicode编码
     * 
     * @param response
     *            需解析报文
     * @param format
     *            解析格式
     * 
     *            key:关键字, 字段长度, [FieldTypes]：字段类型,
     * 
     * @return 报文项
     * @throws UnsupportedEncodingException
     */
    protected static Map unpacketsSequence(byte[] response, Object[][] format)
            throws UnsupportedEncodingException {
        Map responseData = new HashMap();

        int offset = 0;
        byte[] field = new byte[0];
        for (int i = 0; i < format.length; i++) {
            if (FieldTypes.STATIC.equals(format[i][2])) {
                int len = Integer.parseInt((String) format[i][1]);
                field = new byte[len];
                System.arraycopy(response, offset, field, 0, len);
                responseData.put((String) format[i][0],
                        new String(field, "GBK"));
                offset += len;

            } else {// 未定义类型扔出运行时错误
                throw new IllegalArgumentException();
            }

        }
        return responseData;
    }

    /**
     * 合并两个字节数组
     * 
     * @param byteFront
     *            第一个数组
     * @param byteBehind
     *            第二个数组
     * @return 合并后的新数组，长度为byteFront.length + byteBehind.length
     */
    protected static byte[] mergeByte(byte[] byteFront, byte[] byteBehind) {
        byte[] byteMerged = new byte[byteFront.length + byteBehind.length];
        System.arraycopy(byteFront, 0, byteMerged, 0, byteFront.length);
        System.arraycopy(byteBehind, 0, byteMerged, byteFront.length,
                byteBehind.length);
        return byteMerged;

    }

    /**
     * 更新MsgTyp项的值
     * 
     * @param map
     *            表单项
     * @param value
     *            更新后的值
     */
    private void updateMsgTyp(Map map, String value) {
        if (!map.containsKey("MsgTyp")) {// 不存在，直接赋值
            map.put("MsgTyp", value);
            return;
        }
        if (!value.equals(map.get("MsgTyp"))) {// 存在，不同，删除后赋值
            map.remove("MsgTyp");
            map.put("MsgTyp", value);
        }
    }

    /**
     * 按照min（给定长度，原数组长度）截断byte数组
     * 
     * @param original
     *            原byte数组
     * 
     * @param newLength
     *            新数组长度
     * @return
     */
    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

    /**
     * <DL>
     * <DT><B> 填充 </B></DT>
     * <p>
     * <DD>用字符iChar填充满字符串sBufData到isBufLen位，isBufLen为负数左靠齐，isBufLen为正数右靠齐</DD>
     * </DL>
     * <p>
     * 
     * @param sBufData
     *            被填充的字符串
     * @param iChar
     *            填充字符
     * @param isBufLen
     *            填充长度，为负数左靠齐，为正数右靠齐
     * @return 填充后的字符串
     */
    public static String fixFill(String sBufData, String iChar, int isBufLen) {
        String sRetMsg;
        byte bObjData[];
        byte bBufData[];
        if (isBufLen == 0) {
            return null;
        }
        try {
            bBufData = sBufData.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            bBufData = sBufData.getBytes();
        }
        byte bCharData[];
        try {
            bCharData = iChar.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            bCharData = iChar.getBytes();
        }
        int iLen;

        int sBufDataLen = bBufData.length;
        if (isBufLen < 0) {
            iLen = 0 - isBufLen;
            bObjData = new byte[iLen];
            if (sBufDataLen > iLen) {
                sBufDataLen = iLen;
            }
        } else {
            iLen = isBufLen;
            bObjData = new byte[isBufLen];
            if (sBufDataLen > iLen) {
                int iStart = sBufDataLen - iLen;
                for (int i = 0; i < iLen; i++) {
                    bBufData[i] = bBufData[i + iStart];
                }
                sBufDataLen = iLen;
            }
        }
        if (isBufLen < 0) {
            for (int i = 0; i < sBufDataLen; i++) {
                bObjData[i] = bBufData[i];
            }
            for (int i = sBufDataLen; i < iLen; i++) {
                bObjData[i] = bCharData[0];
            }
        } else {
            int iStart = isBufLen - sBufDataLen;
            for (int i = 0; i < iStart; i++) {
                bObjData[i] = bCharData[0];
            }
            for (int i = 0; i < sBufDataLen; i++) {
                bObjData[iStart + i] = bBufData[i];
            }
        }

        try {
            sRetMsg = new String(bObjData, "GBK");
        } catch (UnsupportedEncodingException e) {
            sRetMsg = new String(bObjData);
        }
        return sRetMsg;
    }
}
