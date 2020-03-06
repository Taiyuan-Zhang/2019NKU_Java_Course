import java.util.SplittableRandom;

//手机卡
public class MobileCard {
    public String cardNumber;//卡号
    public String userName;//用户名
    public String passWord;//密码
    public ServicePackage serPackage;//套餐
    public double consumAmount;//总消费
    public double money;//余额
    public int realTalkTime;//通话时长
    public int realSMSCount;//发送短信数
    public int realFlow;//上网流量
    public void showMeg(){
        System.out.println("卡号："+cardNumber+"\n用户名:"+userName+"\n当前余额"+money+"元");
    }//注册成功时的显示

    public void setCardNumber(String cardNumber) {

        this.cardNumber = cardNumber;
    }//注册卡号

    public String getCardNumber() {
        return cardNumber;
    }//获取卡号

    public void setUserName(String userName) {
        this.userName = userName;
    }//注册用户名

    public String getUserName() {
        return userName;
    }//获取用户名

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }//注册设置密码

    public String getPassWord() {
        return passWord;
    }//获取密码

    public void setSerPackage(ServicePackage serPackage) {
        this.serPackage = serPackage;
    }//注册设置套餐

    public ServicePackage getSerPackage() {
        return serPackage;
    }//获取套餐情况

    public void setConsumAmount(double consumAmount) {
        this.consumAmount = consumAmount;
    }//更改总消费

    public double getConsumAmount() {
        return consumAmount;
    }//获取总消费

    public void setMoney(double money) {
        this.money = money;
    }//更改余额

    public double getMoney() {
        return money;
    }//获取余额

    public void setRealTalkTime(int realTalkTime) {
        this.realTalkTime = realTalkTime;
    }//更改通话时长

    public int getRealTalkTime() {
        return realTalkTime;
    }//获取通话时长

    public void setRealSMSCount(int realSMSCount) {
        this.realSMSCount = realSMSCount;
    }//更改发送短信数

    public int getRealSMSCount() {
        return realSMSCount;
    }//获取发送短信数

    public void setRealFlow(int realFlow) {
        this.realFlow = realFlow;
    }//更改上网流量

    public int getRealFlow() {
        return realFlow;
    }//获取上网流量
}
