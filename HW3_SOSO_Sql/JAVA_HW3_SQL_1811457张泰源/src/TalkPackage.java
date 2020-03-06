//话痨套餐
public class TalkPackage extends ServicePackage implements CallService,SendService {
    public int talkTime;// 通话时长
    public int smsCount;// 短信条数

    public TalkPackage() {
        super.price = 58;// 套餐费58
        talkTime = 500; // 通话时长500分钟
        smsCount = 30;// 短信30条
    }

    public void showInfo() {
        System.out.printf("话痨套餐：通话时长为%d分钟/月，短信条数为%d条/月\n", talkTime, smsCount);
    }//显示套餐信息

    public int call(int minCount, MobileCard card){
        int count=0;//计算实际通话分钟数
        for (int i = 0; i < minCount; i++) {
            //判断用户是否可以通话一分钟
            //if有套餐通话时长且余量>=1
            //MobileCard的对象realTalkTime+1
            if(this.talkTime-card.getRealTalkTime()>=1){
                card.setRealTalkTime(card.getRealTalkTime()+1);
                count++;
            }else if(card.getMoney()>=0.2){
                //else if通话时长用完，再看话费余额  如果>=0.2 用话费余额支付
                //MobileCard的对象realTalkTime+1 money-0.2  consumAmount+0.2
                card.setRealTalkTime(card.getRealTalkTime()+1);
                card.setMoney(card.getMoney()-0.2);
                card.setConsumAmount(card.getConsumAmount()+0.2);
                count++;
            }else{
                //else停机
                System.err.println("本次已通话"+count+"分钟，您的电话卡余额不足，请充值！");
                break;
            }
        }
        return count;
    }//通话服务

    public int send(int sendCount, MobileCard card){
        int count=0;//计算发送短信数
        for (int i = 0; i < sendCount; i++) {
            //判断用户是否可以成功发送一条短信
            //if有套餐短信条数且余量>=1
            if(this.smsCount-card.getRealSMSCount()>=1){
                //MobileCard的对象realSMSCount+1
                card.setRealSMSCount(card.getRealSMSCount()+1);
                count++;
            }else if(card.getMoney()>=0.1){
                //else if套餐短信数不够，再看花费余额 如果>=0.1，用话费余额支付
                //MobileCard的对象realSMSCount+1  money-0.1  consumAmount+0.1
                card.setRealSMSCount(card.getRealSMSCount()+1);
                card.setMoney(card.getMoney()-0.1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                count++;
            }else{
                System.err.println("本次已发送短信"+count+"条，您的电话卡余额不足，请充值！");
                break;
            }
        }
        return count;
    }//短信服务

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }//更改通话时长

    public int getTalkTime() {
        return talkTime;
    }//获取通话时长

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }//更改发送短信条数

    public int getSmsCount() {
        return smsCount;
    }//获取发送短信条数
}