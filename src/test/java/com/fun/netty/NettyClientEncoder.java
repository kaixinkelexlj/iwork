/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fun.netty;

import java.nio.ByteBuffer;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 协议编码器
 *
 * @author shijia.wxr<vintage.wang                               @                               gmail.com>
 * @since 2013-7-13
 */
public class NettyClientEncoder extends MessageToByteEncoder<RemotingCommand> {

    private static final Log log = LogFactory.getLog(NettyClientEncoder.class);

    @Override
    public void encode(ChannelHandlerContext ctx, RemotingCommand remotingCommand, ByteBuf out)
        throws Exception {
        try {
            //TODO lujun.xlj
            if (remotingCommand != null) {
                if (StringUtils.isNotBlank(remotingCommand.getValue())) {
                    out.writeBytes(ByteBuffer.wrap((JSON.toJSONString(remotingCommand) + "\n").getBytes(NettySystemConfig.DefaultCharset)));
                }
            }
            //out.writeBytes("hello from simple encoder".getBytes(Charset.forName("utf8")));
        } catch (Throwable e) {
            log.error("encode exception, " + RemotingUtil.parseChannelRemoteAddr(ctx.channel()), e);
            if (remotingCommand != null) {
                log.error(remotingCommand.toString());
            }
            // 这里关闭后， 会在pipeline中产生事件，通过具体的close事件来清理数据结构
            RemotingUtil.closeChannel(ctx.channel());
        }
    }
}
