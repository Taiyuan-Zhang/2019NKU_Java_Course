import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CardSql extends BaseDao implements CardDao {
    @Override
    public Map<String, MobileCard> getall() {
        String sql="SELECT id,number,money,pwd,talktime,send,flow,consume,package FROM users";
        Map<String, MobileCard>Map=new HashMap<String, MobileCard>();
        try{
            rs=this.search(sql,null);
            while(rs.next()){
                String pack=rs.getString("package");
                MobileCard card=new MobileCard();
                ServicePackage Package=null;
                if(pack.equals("talkpackage"))Package=new TalkPackage();
                if(pack.equals("superpackage"))Package=new SuperPackage();
                if(pack.equals("netpackage"))Package=new NetPackage();
                card.setUserName(rs.getString("id"));
                card.setCardNumber(rs.getString("number"));
                card.setMoney(rs.getDouble("money"));
                card.setPassWord(rs.getString("pwd"));
                card.setRealTalkTime(rs.getInt("talktime"));
                card.setRealSMSCount(rs.getInt("send"));
                card.setRealFlow(rs.getInt("flow"));
                card.setConsumAmount(rs.getDouble("consume"));
                card.setSerPackage(Package);
                Map.put(card.getCardNumber(),card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return Map;
    }

    @Override
    public int addUser(MobileCard mobileCard) {
        String pack=null;ServicePackage Package=mobileCard.getSerPackage();
        if(Package.getPrice()==new NetPackage().getPrice()) {
            pack="netpackage";
        }
        if(Package.getPrice()==new SuperPackage().getPrice()) {
            pack="superpackage";
        }
        if(Package.getPrice()==new TalkPackage().getPrice()) {
            pack="talkpackage";
        }
        String sql="INSERT INTO users (id,number,pwd,money,consume,talktime,send,flow,package) VALUES(?,?,?,?,?,?,?,?,?);";
        Object[] params={mobileCard.getUserName(),mobileCard.getCardNumber(),mobileCard.getPassWord(),mobileCard.getMoney(),mobileCard.getConsumAmount(),mobileCard.getRealTalkTime(),mobileCard.getRealSMSCount(),mobileCard.getRealFlow(),pack};
        int result=0;
        try {
            result = this.update(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateUser(MobileCard mobileCard) {
        String pack=null;ServicePackage Package=mobileCard.getSerPackage();
        if(Package.getPrice()==new NetPackage().getPrice()) {
            pack="netpackage";
        }
        if(Package.getPrice()==new SuperPackage().getPrice()) {
            pack="superpackage";
        }
        if(Package.getPrice()==new TalkPackage().getPrice()) {
            pack="talkpackage";
        }
        String sql="UPDATE users SET money=?,talktime=?,send=?,consume=?,flow=?,package=? WHERE number=?";
        Object[] params={mobileCard.getMoney(),mobileCard.getRealTalkTime(),mobileCard.getRealSMSCount(),mobileCard.getConsumAmount(),mobileCard.getRealFlow(),pack,mobileCard.getCardNumber()};
        int result=0;
        try {
            result = this.update(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delcard(MobileCard mobileCard) {
        String sql="DELETE FROM users WHERE number=?";
        Object[] params={mobileCard.getCardNumber()};
        int result=0;
        try {
            result = this.update(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
