package ntu.jky.bean;

/**
 * 返回给前端的信息类
 */
public class Message {
    private boolean flag;
    private String msg;

    public Message() {
    }

    public Message(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
