package com.gdbocom.util.communication;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import com.viatt.util.GzLog;

/**
 * IcsServer类用于针对ICS服务器进行通讯
 * 
 * @author qm
 * 
 */
public class IcsServer {
    private String host;
    private int post;

    private GzLog gzLog = new GzLog("c:/gzLog_sj");

    /**
     * 通过指定主机ip和端口创建IcsServer实例
     * 
     * @param host
     * @param post
     */
    public IcsServer(String host, int post) {
        this.host = host;
        this.post = post;
    }

    /**
     * 通过标签配置实例化IcsServer对象，该对象将使用配置文件中的IP和端口
     * 
     * @param configTag
     *            标签名
     * @return 实例化后的IcsServer对象
     */
    public static IcsServer getServer(String configTag) {
        String host_post = ConfigProps.getInstance().getProperty(configTag);
        String[] hostPost = host_post.split(",");
        return new IcsServer(hostPost[0], Integer.valueOf(hostPost[1])
                .intValue());

    }

    /**
     * 发送报文至服务器并获取返回报文
     * 
     * @param request
     *            发送报文字节数组(GBK)
     * @return 返回报文字节数组(GBK)
     */
    public byte[] send(byte[] request) throws UnknownHostException, IOException {

        // 添加请求报文前置长度，并转码成GBK字节
        int preLength = 8;

        byte[] requestPacket = new byte[preLength + request.length];
        // 复制前置长度
        StringBuffer lenstr = new StringBuffer(String.valueOf(request.length));
        lenstr.insert(0, "00000000");
        lenstr.delete(0, lenstr.length() - 8);
        System.arraycopy(lenstr.toString().getBytes("GBK"), 0, requestPacket,
                0, preLength);

        // 复制报文字节
        System.arraycopy(request, 0, requestPacket, preLength, request.length);

        // 与ICS进行通讯，并对接收报文进行转码
        Socket icsSocket = null;
        OutputStream os = null;
        InputStream is = null;

        try {

            icsSocket = new Socket(this.host, this.post);
            os = icsSocket.getOutputStream();
            is = icsSocket.getInputStream();

            os.write(requestPacket, 0, requestPacket.length);
            os.flush();

            // 先通过前置长度获取返回包长度
            byte[] responsePreLength = new byte[preLength];
            int islen = is.read(responsePreLength);
            if (islen <= 0) {
                gzLog.Write(new String("对方无返回!".getBytes("UTF-8"),
                        "GBK"));
                throw new IOException();
            }
            // 得到报文总长度
            int responseLength = Integer.parseInt(new String(responsePreLength,
                    "GBK"));

            // 获取返回报文
            byte[] responsePacket = new byte[responseLength];
            int hasRead = is.read(responsePacket);

            if (hasRead > 0) {
                return responsePacket;
            } else {
                return "".getBytes("GBK");
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "".getBytes("GBK");
        } catch (IOException e) {
            e.printStackTrace();
            return "".getBytes("GBK");
        } finally {
            if (null != os) {
                os.close();
            }
            if (null != is) {
                is.close();
            }
            if (null != icsSocket) {
                icsSocket.close();
            }
        }

    }

}
