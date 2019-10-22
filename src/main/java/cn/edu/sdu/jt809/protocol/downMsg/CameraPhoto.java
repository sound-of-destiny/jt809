package cn.edu.sdu.jt809.protocol.downMsg;

import cn.edu.sdu.jt809.protocol.MsgHeader;
import cn.edu.sdu.jt809.util.MessageId;
import jtframework.annotation.MsgType;
import jtframework.message.PackageData;

@MsgType(MessageId.DOWN_CTRL_MSG_TAKE_PHOTO_REQ)
public class CameraPhoto extends PackageData<MsgHeader> {
}
