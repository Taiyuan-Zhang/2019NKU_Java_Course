//网虫套餐
public class NetPackage extends ServicePackage implements NetService{
    public int flow;//上网流量

    public NetPackage(){
        super.price=68;//套餐费68
        flow=3*1024;//流量3G，单位MB
    }

    public void showInfo(){
        System.out.println("网虫套餐：上网流量为" + flow / 1024 + "GB/月");
    }//显示套餐信息

    public int netPlay(int flow, MobileCard card){
        int count=0;////计算上网流量
        for (int i = 0; i < flow; i++) {
            //判断用户是否可以成功使用1MB流量
            //if有套餐流量且余量>=1
            if(this.flow-card.getRealFlow()>=1){
                // MobileCard的对象realFlow+1
                card.setRealFlow(card.getRealFlow()+1);
                count++;
            }else if(card.getMoney()>=0.1){
                //else if没有套餐流量，再看话费余额，话费余额>=0.1
                //MobileCard的对象realFlow+1  monry-0.1  consumAmount+0.1
                card.setRealFlow(card.getRealFlow()+1);
                card.setMoney(card.getMoney()-0.1);
                card.setConsumAmount(card.getConsumAmount()+0.1);
                count++;
            }else{
                System.err.println("本次已用流量"+count+"MB，您的电话卡余额不足，请充值！");
                break;
            }

        }
        return count;

    }//上网服务

    public void setFlow(int flow) {
        this.flow = flow;
    }//更改流量

    public int getFlow() {
        return flow;
    }//获取流量
}
