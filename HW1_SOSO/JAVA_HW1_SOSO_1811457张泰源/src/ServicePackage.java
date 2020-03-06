//套餐情况
public  abstract class ServicePackage {
    public double price;//套餐费

    public abstract void showInfo();//显示套餐信息

    public void setPrice(double price){
        this.price=price;
    }//初始化套餐费

    public double getPrice(){
        return  price;
    }//获取套餐费
}
