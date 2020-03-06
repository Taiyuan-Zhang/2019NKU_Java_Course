import java.io.*;
import java.util.*;

public class CardUtil {
    public Map<String,MobileCard>cards=new HashMap<String,MobileCard>();
    public Map<String,List<ConsumInfo>>consumInfos=new HashMap<String, List<ConsumInfo>>();
    public List<ServicePackage>servicePackages=new ArrayList<ServicePackage>();
    public List<Scene>scenes=new ArrayList<Scene>();

    public void init(){
        servicePackages.add(new TalkPackage());
        servicePackages.add(new NetPackage());
        servicePackages.add(new SuperPackage());
    }//初始化套餐

    public void initScene(){
        scenes.add(new Scene("通话", 90, "问候客户，谁知其如此难缠，通话90分钟"));
        scenes.add(new Scene("短信", 50, "通知朋友手机换号，发送短信50条"));
        scenes.add(new Scene("短信", 5, "参与环境保护实施方案问卷调查，发送短信5条"));
        scenes.add(new Scene("通话", 5, "询问妈妈身体状况，本地通话30分钟"));
        scenes.add(new Scene("上网", 100, "上网冲浪看小说，使用流量100MB"));
        scenes.add(new Scene("上网", 500, "观看《我和我的祖国》预告片，使用流量500M"));
    }

    public boolean isExistCard(String number,String passWord){
        MobileCard myCard=cards.get(number);
        if(myCard!=null&&myCard.getPassWord().equals(passWord)){
            return true;
        }
        return false;

    }//根据卡号密码验证是否注册

    public boolean isExistCard(String number){
        //containsKey()判断是否存在，返回值为boolean
        return cards.containsKey(number);

    }//根据号码判断是否注册

    public String createNumber() {
        //随机生成8个0-9之间的整数，用StringBuilder拼接
        //判断该号码是否已经被注册，如果被注册了，再调用createNumber()生成一个新的手机号
        StringBuilder myNumber = new StringBuilder("139");
        for (int i = 1; i <= 8; i++) {
            myNumber.append((int) (Math.random() * 10));
        }
        String cardNumber = myNumber.toString();
        if (isExistCard(cardNumber)) {
            cardNumber = createNumber();
        }
        return cardNumber;
    }
    public String[] getNewNumbers(int count) {
        String numbers[] = new String[count];
        //循环count次，调用createNumber()， 生成count个随机的新卡号且不重复
        int num = 0;// 计算有效的卡号数
        String s = "0";
        while (num < numbers.length) {
            boolean flag = true;
            // 检查数组里是否存在，true表示不存在，false表示存在
            s = createNumber();
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == null) {
                    break;
                }
                if (numbers[i].equals(s)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                numbers[num] = s;
                num++;
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = createNumber();
        }
        return numbers;
    }//生成新卡号

    public void addCard(MobileCard card) {
        cards.put(card.getCardNumber(), card);
    }//注册新卡

    public void delCard(String number) {
        if (!cards.containsKey(number)) {
            System.out.println("您的卡号不存在");
        } else {
            cards.remove(number);
            System.out.println("卡号" + number + "办理退网成功！");
            System.out.println("谢谢使用！");
        }
    }//办理退网

    public void showRemainDetail(String number) {
        //打印卡号，套餐内剩余通话时长，短信条数，上网流量（MB）
        MobileCard mc = cards.get(number.trim());
        ServicePackage serPack = mc.getSerPackage();
        int remainTalk = 0, remainSms = 0; // 计算剩余通话时长和剩余短信
        int remainFlow = 0;// 统计剩余流量
        if (serPack instanceof TalkPackage) {
            TalkPackage tp = (TalkPackage) serPack;
            remainTalk = Math.max(tp.getTalkTime() - mc.getRealTalkTime(), 0);
            remainSms = Math.max(tp.getSmsCount() - mc.getRealSMSCount(), 0);
        } else if (serPack instanceof NetPackage) {
            NetPackage np = (NetPackage) serPack;
            remainFlow = Math.max(np.getFlow() - mc.getRealFlow(), 0);
        } else if (serPack instanceof SuperPackage) {
            SuperPackage sp = (SuperPackage) serPack;
            remainTalk = Math.max(sp.getTalkTime() - mc.getRealTalkTime(), 0);
            remainSms = Math.max(sp.getSmsCount() - mc.getRealSMSCount(), 0);
            remainFlow = Math.max(sp.getFlow() - mc.getRealFlow(), 0);
        }
        System.out.println("您的卡号是" + number + "，套餐内剩余：");
        System.out.println("通话时长：" + remainTalk + "分钟");
        System.out.println("短信条数：" + remainSms + "条");
        System.out.println("上网流量：" + remainFlow + "MB");
    }

    public void showAmountDetail(String number) {
        //显示总消费明细
        MobileCard mc = cards.get(number.trim());
        System.out.println("您的卡号：" + mc.getCardNumber() + "，当月账单：");
        System.out.println("套餐资费:" + mc.getSerPackage().getPrice() + "元");
        System.out.println("合计：" + ConsumInfo.dataFormat(mc.getConsumAmount())+ "元");
        System.out.println("账户余额：" + ConsumInfo.dataFormat(mc.getMoney())+ "元");
    }

    public void addConsumInfo(String number, ConsumInfo info) {
        if (consumInfos.containsKey(number)) {
            // 如果该卡号有消费记录>=0,取出消费记录的List
            // 先把消费信息保存到List中，再保存到Map中
            List<ConsumInfo> consumlist = consumInfos.get(number);
            consumlist.add(info);
            consumInfos.put(number, consumlist);
            System.out.println("已更新此卡的消费记录！");
        } else {
            // 如果该卡号没有消费记录，new一个List集合
            // 先把消费信息保存到new的List中，再put到Map中
            List<ConsumInfo> consumlist = new ArrayList<ConsumInfo>();
            consumlist.add(info);
            consumInfos.put(number, consumlist);
            System.out.println("不存在此卡的消费记录，已添加一条消费记录。");
        }
    }

    public void useSoso(String number) {
        MobileCard card = cards.get(number);
        if (card == null) {
            System.out.println("您未注册嗖嗖，请先注册后再使用");
            return;
        }
        //随机生成0-5的下标选择一个场景
        //判断用户的套餐是否支持场景，如果不支持，再换一个场景，直到找到用户支持的场景
        int data = 0;// 计算各场景中的实际消费数据
        while (true) {
            int index = new Random().nextInt(6);
            Scene scene = scenes.get(index);
            switch (scene.getType()) {
                case "通话":
                    if (card.getSerPackage() instanceof CallService) {
                        // 如果卡支持通话功能
                        // 输出场景描述
                        System.out.println(scene.getDescription());
                        CallService callService = (CallService) card
                                .getSerPackage();
                        try {
                            data = callService.call(scene.getData(), card);
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                        // System.out.println(data);
                        if (data > 0) {
                            // 添加一条消费记录
                            addConsumInfo(number,
                                    new ConsumInfo(number, scene.getType(), data));
                        }
                        // 如果余额不足0.2元 不能使用嗖嗖打电话
                        if (card.getMoney() < 0.2) {
                            return;
                        }
                    } else {// 如果卡不支持通话功能，继续随机生成其他场景
                        continue;
                    }
                    break;
                case "短信":
                    if (card.getSerPackage() instanceof SendService) {
                        // 如果卡支持短信功能
                        // 输出场景描述
                        System.out.println(scene.getDescription());

                        SendService sendService = (SendService) card
                                .getSerPackage();
                        try {
                            data = sendService.send(scene.getData(), card);
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                        // System.out.println(data);
                        // 添加一条消费记录
                        if (data > 0) {
                            addConsumInfo(number,new ConsumInfo(number, scene.getType(), data));
                        }
                        // 如果余额不足0.1元 不能使用嗖嗖发短信
                        if (card.getMoney() < 0.1) {
                            return;
                        }
                    } else {// 如果卡不支持短信功能，继续随机生成其他场景
                        continue;
                    }
                    break;
                case "上网":
                    if (card.getSerPackage() instanceof NetService) {
                        // 如果卡支持上网功能
                        // 输出场景描述
                        System.out.println(scene.getDescription());
                        NetService netService = (NetService) card.getSerPackage();
                        try {
                            data = netService.netPlay(scene.getData(), card);
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                        // System.out.println(data);
                        // 添加一条消费记录
                        if (data > 0) {
                            addConsumInfo(number,
                                    new ConsumInfo(number, scene.getType(), data));
                        }
                        // 如果余额不足0.1元 不能使用嗖嗖上网
                        if (card.getMoney() < 0.1) {
                            return;
                        }
                    } else {// 如果卡不支持上网功能，继续随机生成其他场景
                        continue;
                    }
                    break;
            }
            break;
        }
    }

    public void showDescription(){
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader("套餐资费说明.txt"));
            String info = br.readLine();
            while (info != null) {
                System.out.println(info);
                info = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("没有找到套餐资费说明文件");
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void changingPack(String number, String packNum) {
        MobileCard card = cards.get(number);
        ServicePackage oldPack = card.getSerPackage();
        ServicePackage newPack = servicePackages.get(Integer.parseInt(packNum)-1);
        // 如果手机卡套餐的资费和选择的套餐资费相等 说明是同一个套餐
        if (oldPack.getPrice() == newPack.getPrice()) {
            System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
            return;
        } else if (card.getMoney() < newPack.getPrice()) {
            System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
            return;
        } else {
            // 重新设置套餐
            card.setSerPackage(newPack);
            // 重新设置账户余额=原来账户余额—新套餐资费
            card.setMoney(card.getMoney() - newPack.getPrice());
            // 重新设置当月消费金额=原消费金额+套餐资费
            card.setConsumAmount(card.getConsumAmount() + newPack.getPrice());
            // 将实际已用的通话时长、短信、流量清零
            card.setRealTalkTime(0);
            card.setRealSMSCount(0);
            card.setRealFlow(0);
            System.out.print("更换套餐成功！");
            newPack.showInfo();
        }
    }//变更套餐

    public void printConsumInfo(String number) {
        // 取出卡号对应的消费明细记录
        List<ConsumInfo> consumlist = consumInfos.get(number);
        if (consumlist == null) {
            System.out.println("对不起，不存在此号码的消费记录，无法打印！");
            return;
        }
        StringBuilder sb = new StringBuilder("*****" + number + "消费记录****\n");
        sb.append("序号\t类型\t 数据(通话(条)/上网(MB)/短信(条))\n");
        for (int i = 0; i < consumlist.size(); i++) {
            sb.append((i + 1) + "\t" + consumlist.get(i).getType() + "\t"
                    + consumlist.get(i).getConsumData() +"\n");
        }
        System.out.println(sb);
        // 将输出的内容保存到StringBuilder中
        Writer wr = null;
        try {
            wr = new FileWriter(number + "消费记录.txt");
            wr.write(sb.toString());
            wr.flush();
            System.out.println("消费记录打印成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(wr!=null){
                try {
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }//打印消费明细

    public void chargeMoney(String number, double money) {
        MobileCard card = cards.get(number);
        if (card == null) {
            System.out.println("您的卡号输入有误，请重新充值！");
            return;
        }
        card.setMoney(card.getMoney() + money);
        System.out.println("充值成功，当前话费余额为" + card.getMoney() + "元");
    }
}

