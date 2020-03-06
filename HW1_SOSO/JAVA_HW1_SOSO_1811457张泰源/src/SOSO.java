import java.util.List;
import java.util.Scanner;

public class SOSO {
    public static Scanner sc = new Scanner(System.in);
    public static CardUtil util = new CardUtil();
    public static CardSql dao=new CardSql();
    public static void main(String[] args) {
        util.cards=dao.getall();
        util.init();// 初始化
        util.initScene();// 初始化场景
        while (true) {
            mainMenu();
        }
    }


    public static void mainMenu() {
        System.out.println("*************欢迎使用嗖嗖移动业务大厅*************");
        System.out.println("1.用户登录  2.用户注册  3.使用嗖嗖  4.话费充值   5.资费说明  6.退出系统");
        String choice = sc.next();
        switch (choice.trim()) {
            case "1":
                login();
                break;
            case "2":
                System.out.println("*****用户注册*****");
                registe();
                mainMenu();
                break;
            case "3":
                System.out.println("*****使用嗖嗖*****");
                useSoso();
                mainMenu();
                break;
            case "4":
                System.out.println("*****话费充值*****");
                chargeMoney();
                mainMenu();
                break;
            case "5":
                System.out.println("*****资费说明*****");
                util.showDescription();
                mainMenu();
                break;
            case "6":
                System.out.println("谢谢使用");
                System.exit(0);
                break;
            default:
                System.out.println("您的输入有误！");
                mainMenu();
                break;

        }
    }

    //登录
    public static void login() {
        //提示用户输入卡号，密码 ，验证卡号密码是否正确，如果输入错误，提示重新输入 ，如果登录成功 显示用户菜单
        System.out.println("请输入手机卡号：");
        String number = sc.next();
        System.out.println("请输入密码：");
        String pwd = sc.next();
        boolean exist = util.isExistCard(number);// 验证卡号是否存在
        if (!exist) {
            System.out.println("您未注册嗖嗖，请先注册后再使用");
            mainMenu();
        } else {
            boolean flag = util.isExistCard(number, pwd);// 验证卡号和密码是否正确
            if (flag) {
                while (true) {
                    System.out.println("登录成功！");
                    cardMenu(number);
                }
            } else {
                System.out.println("手机卡号或密码输入有误");
                mainMenu();
            }
        }

    }

    //登录后的菜单
    public static void cardMenu(String number) {
        System.out.println("*****嗖嗖移动用户菜单*****");
        System.out.println("1.本月账单查询");
        System.out.println("2.套餐余量查询");
        System.out.println("3.打印消费详单");
        System.out.println("4.套餐变更");
        System.out.println("5.办理退网");
        System.out.println("请选择：(输入1~5选择功能，其他键返回上一级)");
        String choice = sc.next();
        switch (choice.trim()) {
            case "1":
                System.out.println("*****本月账单查询*****");
                util.showAmountDetail(number);
                break;
            case "2":
                System.out.println("*****套餐余量查询*****");
                util.showRemainDetail(number);
                break;
            case "3":
                System.out.println("*****打印消费详单*****");
                util.printConsumInfo(number);
                break;
            case "4":
                System.out.println("*****套餐变更*****");
                changePack(number);
                break;
            case "5":
                System.out.println("*****办理退网*****");
                MobileCard mobileCard=util.cards.get(number);
                dao.delcard(mobileCard);
                util.delCard(number);
                mainMenu();
                break;
            default:
                mainMenu();
                break;
        }
    }

    //注册
    public static void registe() {
        //列出9个备选手机号，用户选号
        //列出3个套餐，用户选择套餐
        //提示用户输入姓名，查询密码,预存话费金额（最低100元）
        //保存卡信息：CardUtil.addCard()
        //如果开卡成功，打印开卡信息
        System.out.println("*****可选择的卡号*****");
        // 获取9个随机卡号
        String numbers[] =  util.getNewNumbers(9);
        // 按照每行3个卡号打印出来
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + "." + numbers[i] + "\t");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println("请选择卡号（输入1~9的序号）：");
        int number = 0;// 用于保存后面输入的序号
        while (true) {
            String choice = sc.next();
            // 判断是否在1到9的范围内
            number = Integer.parseInt(choice);
            if (number > 9 || number < 1) {
                System.out.println("您的输入有误，请重新输入！");
                continue;
            }
            else{
                break;
            }
        }

        String cardNumber = numbers[number - 1];// 保存用户选择的卡号
        System.out.println("1.话痨套餐  2.网虫套餐  3.超人套餐");
        System.out.print("请选择套餐（输入序号）：");
        String packId = sc.next();
        ServicePackage sp = util.servicePackages.get(Integer.parseInt(packId) - 1);
        while (true) {// 如果套餐输入错误 循环重新输入
            if (sp == null) {
                System.out.println("您的输入有误，请重新输入！");
                packId = sc.next();
                sp = util.servicePackages.get(Integer.parseInt(packId) - 1);
            } else {
                break;
            }
        }
        System.out.println("请输入姓名：");
        String userName = sc.next();
        System.out.println("请输入密码：");
        String passWord = sc.next();
        System.out.println("请输入预存话费金额：");
        double price = sp.getPrice();// 获取固定套餐资费
        int premoney=0;
        while (true) {
            String moneyString = sc.next();
            premoney=Integer.parseInt(moneyString);
            if (premoney < price) {
                System.out.println("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
                continue;
            }
            else {
                break;
            }
        }
        MobileCard mc = new MobileCard();
        mc.setCardNumber(cardNumber);
        mc.setUserName(userName);
        mc.setPassWord(passWord);
        mc.setSerPackage(sp);
        mc.setMoney(premoney - price);
        mc.setConsumAmount(price); // 此时当月消费总额等于套餐资费
        util.addCard(mc);
        dao.addUser(mc);
        mc.showMeg();// 显示本卡信息
        mc.getSerPackage().showInfo();// 显示套餐信息
        System.out.println("跳入登录界面");
        login();
    }

    //使用嗖嗖
    public static void useSoso() {
        System.out.println("请输入手机卡号：");
        String number = sc.next();
        util.useSoso(number);
        MobileCard mobileCard=util.cards.get(number);
        dao.updateUser(mobileCard);
    }

    //变更套餐
    public static void changePack(String number) {
        System.out.println("1.话痨套餐  2.网虫套餐  3.超人套餐  请选择(序号)：");
        String packNum = sc.next();
        util.changingPack(number, packNum);
        MobileCard mobileCard=util.cards.get(number);
        dao.updateUser(mobileCard);

    }

   //充值
    public static void chargeMoney() {
        System.out.println("请输入充值卡号：");
        String number = sc.next();
        System.out.println("请输入充值金额：");
        int money=0;
        while (true) {
            String moneyString = sc.next();
            money=Integer.parseInt(moneyString);
            if (money < 50) {
                System.out.println("充值金额最少50元，请重新充值！");
                continue;
            }
            else {
                break;
            }
        }
        util.chargeMoney(number, money);
        MobileCard mobileCard=util.cards.get(number);
        dao.updateUser(mobileCard);
    }
}
