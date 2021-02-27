package com.abchina.handler;

import com.abchina.http.Request;
import com.abchina.http.RequestFacade;
import com.abchina.utils.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 请求解析器
 *
 * @author jerrylz
 * @date 2021/2/26
 */
public class RequestDecoderHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtils.info(this.getClass(), "执行RequestDecoderHandler");

        //解析请求数据转换SimpleHttpRequest对象
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf)msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String requestRaw = new String(req,"UTF-8");
            RequestFacade request = new RequestFacade(requestRaw);
            ctx.fireChannelRead(request);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

}
