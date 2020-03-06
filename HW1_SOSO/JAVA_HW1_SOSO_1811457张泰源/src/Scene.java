//场景类
public class Scene {
    public String type;//场景类型
    public int data;//场景消费数据
    public String description;//场景描述

    public Scene(String type, int data, String description){
        super();
        this.type=type;
        this.data=data;
        this.description=description;
    }

    public void setType(String type) {
        this.type = type;
    }//更改场景类型

    public String getType(){
        return type;
    }//获取场景类型

    public void setData(int data) {
        this.data = data;
    }//更改消费数据

    public int getData() {
        return data;
    }//获取消费数据

    public void setDescription(String description) {
        this.description = description;
    }//更改场景描述

    public String getDescription() {
        return description;
    }//获取场景描述
}
