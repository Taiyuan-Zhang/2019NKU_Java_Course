import java.util.Map;

public interface CardDao {
    //查看用户信息
    public Map<String,MobileCard> getall();
    //添加用户
    public int addUser(MobileCard mobileCard);
    //修改信息
    public int updateUser(MobileCard mobileCard);
    //退网
    public int delcard(MobileCard mobileCard);
}
