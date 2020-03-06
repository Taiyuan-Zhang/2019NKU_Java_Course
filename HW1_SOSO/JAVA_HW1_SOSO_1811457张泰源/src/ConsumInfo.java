import java.text.DecimalFormat;

public class ConsumInfo {
    public String cardNumber;//号码
    public String type;//消费类型
    public int consumData;//消费数据

    public ConsumInfo(String cardNumber,String type,int consumData){
        this.cardNumber=cardNumber;
        this.type=type;
        this.consumData=consumData;
    }
    public static String dataFormat(double data){
        DecimalFormat formater=new DecimalFormat("0.0");
        return formater.format(data);
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber=cardNumber;
    }//更改号码

    public String getCardNumber(){
        return cardNumber;
    }//获取号码

    public void setType(String type){
        this.type=type;
    }//更改消费类型

    public String getType(){
        return type;
    }//获取消费类型

    public void setConsumData(int consumData) {
        this.consumData = consumData;
    }//更改消费数据

    public int getConsumData() {
        return consumData;
    }//获取消费数据
}
