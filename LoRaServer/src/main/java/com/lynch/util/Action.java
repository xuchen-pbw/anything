package com.lynch.util;

/**
 * Created by lynch on 2019-04-11. <br>
 **/
public class Action {
    public static final class CODE {
        static final public String OK = "0/OK";
        static final public String timeOut = "1/time out";
        static final public String noSuchDevice = "2/No such Device";
        static final public String ATCmdFormatError = "3/AT command format error";
        static final public String IRNotSupportCmd = "4/IR do not support this cmd";
        static final public String IRCodeError = "5/IR code error or fail to send the IR signal";
        static final public String JsonMessageFormatError = "6/Json Message format error";
        static final public String NotSupportMessageType = "7/Not supported Message type";
        static final public String MessageWithoutDeviceId = "8/Cannot find device id in message";
        static final public String MessageWithoutKey = "9/Cannot find key in message";
        static final public String MessageWithoutReply_to = "10/the message should with Reply_to";
        static final public String MessageKeyError = "11/the key of the message is error or the key does not present for the device";
        static final public String KeyNotReadable = "12/the key is not readable";
        static final public String KeyNotWriteable = "13/the key is not writeable";
        static final public String RequiredMessageFieldMissing = "14/required messge field missing";
        static final public String AgentOffLine = "15/the agent of this device is not online";
        static final public String InternalDBException = "16/Internal database error";
        static final public String TimoutValueIllegal = "17/Timeout Value Illegal";
        static final public String NotSuchDeviceOrKey = "18/cannot find such a device or key";
        static final public String DeviceDataInvalid = "19/Device data is Invalid";
        static final public String NotSupportedKey = "20/Not supported Key";
        static final public String InvalidValue = "21/the value is invalid";
        static final public String AddressMappingFail = "22/Address mapping fail";
        static final public String DeviceIsBusy = "23/the device is too busy";
        static final public String NoMachedProfile = "24/cannot find mached profile according to the message";
        static final public String PasswordInvalid = "25/the password is invalid";
        static final public String PasswordSameWithPrevious = "26/the new password is same with previous one";
        static final public String ResponseMessageTypeInconsistent = "27/The response Message type is inconsistent with requested one";
        static final public String DeviceSpecifiedError = "98/Devive Specified Error/";
        static final public String UnknownError = "99/Unknown Error";

        static final public String noSuchFirmware = "101/Cannot find the Firmware";

    }
}