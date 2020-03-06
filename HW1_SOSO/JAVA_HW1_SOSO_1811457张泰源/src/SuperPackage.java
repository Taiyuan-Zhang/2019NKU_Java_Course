//超人套餐
public class SuperPackage extends ServicePackage implements CallService,
        SendService, NetService{
    public int talkTime; // 通话时间
    public int smsCount;// 短信数量
    public int flow; // 上网流量

    public SuperPackage() {
        super.price = 78;// 套餐费78
        talkTime = 200;// 通话时长200分钟
        flow = 1024;// 上网流量1G 单位是MB
        smsCount = 50;// 短信50条
    }

    public void showInfo() {
        System.out.printf("超人套餐：通话时长为%d分钟/月，短信条数为%d，上网流量为%sGB/月\n", talkTime,
                smsCount, flow*1.0/1024);
    }//显示套餐信息

    public int call(int minCount, MobileCard card){
        int count=0;//保存实际通话分钟总数
        for (int i = 0; i < minCount; i++) {
            //判断用户是否可以成功通话一分钟
            //1.有套餐通话时长，余量够一分钟
            //手机卡对象中实际通话时长+1
            if(this.talkTime-card.getRealTalkTime()>=1){
                card.setRealTalkTime(card.getRealTalkTime()+1);
                count++;
            }else if(card.getMoney()>=0.2){
                /*2.通话时长用完，再看话费余额  如果够支付一分钟(0.2元) 用话费余额支付
                 *手机卡对象中实际通话时长+1 手机卡中余额-0.2元   手机卡实际消费金额+0.2元
                 */
                card.setRealTalkTime(card.getRealTalkTime()+1);
                card.setMoney(card.getMoney()-0.2);
                card.setConsumAmount(card.getConsumAmount()+0.2);
                count++;
            }else{
                //3.不够则停机
                System.err.println("本次已通话"+count+"分钟，您的余额不足，请充值后再使用！");
                break;
            }
        }
        return count;
    }//通话服务

    public int send(int sendCount, MobileCard card){
        int count=0;//保存实际发送短信总数
        for (int i = 0; i < sendCount; i++) {
            //判断用户是否可以成功发送一条短信
            //1.有套餐短信条数，余量够发一条短信
            if(this.smsCount-card.getRealSMSCount()>=1){
                //手机卡对象中实际发送短信数量+1
                card.setRealSMSCount(card.getRealSMSCount()+1);
                count++;
            }else if(card.getMoney()>=0.1){
                //2.话费余额够不够支付发送一条短信：0.1元
                //手机卡对象中实际发送短信数量+1
                //手机卡中余额-0.1元  手机卡实际消费金额+0.1元
                card.setRealSMSCount(card.getRealSMSCount()+1);
                card.setMoney(card.getMoney()-0.1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                count++;
            }else{
                System.err.println("本次已用短信"+count+"条，您的余额不足，请充值后再使用！");
                break;
            }

        }
        return count;
    }//短信服务

    public int netPlay(int flow, MobileCard card){
        int count=0;////保存实际上网流量总数
        for (int i = 0; i < flow; i++) {
            //判断用户是否可以成功使用1MB流量
            //1.有套餐流量，余量够1MB
            if(this.flow-card.getRealFlow()>=1){
                //手机卡对象中实际使用流量+1
                card.setRealFlow(card.getRealFlow()+1);
                count++;
            }else if(card.getMoney()>=0.1){
                //2.没有套餐流量，话费余额够不够支付1MB流量：0.1元
                //手机卡对象中实际使用流量+1
                //手机卡中余额-0.1元  手机卡实际消费金额+0.1元
                card.setRealFlow(card.getRealFlow()+1);;
                card.setMoney(card.getMoney()-0.1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                count++;
            }else{
                System.err.println("本次已用流量"+count+"MB，您的余额不足，请充值后再使用！");
                break;
            }
        }
        return count;
    }//上网服务

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }//更改通话时长

    public int getTalkTime() {
        return talkTime;
    }//获取通话时长

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }//更改短信条数

    public int getSmsCount() {
        return smsCount;
    }//获取短信条数

    public void setFlow(int flow) {
        this.flow = flow;
    }//更改流量数

    public int getFlow() {
        return flow;
    }//获取流量数
}
