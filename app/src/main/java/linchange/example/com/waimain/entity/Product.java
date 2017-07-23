package linchange.example.com.waimain.entity;

import android.os.Parcel;
import android.os.Parcelable;

//商品类
public class Product implements Parcelable {
    private int id; //商品表示符
    private String name; //商品名
    private double price; //价格
    private int picture; //商品图片
    private int sale; //月销量
    private String shopName; //店铺名称
    private String detail; //产品描述
    private int selectedCount = 0; //已选择该商品的数量
    private boolean isShowSubBtn; //是否显示减去按钮

    public Product(int id, String name, double price, int picture, int sale, String shopName, String detail, boolean isShowSubBtn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.sale = sale;
        this.shopName = shopName;
        this.detail = detail;
        this.isShowSubBtn = isShowSubBtn;
    }

    //获取该商品被选中的次数
    public int getSelectedCount() {
        return selectedCount;
    }

    //将商品被选中次数清零与设置不显示减去按钮
    public void clearZero() {
        selectedCount = 0;
        isShowSubBtn = false;
    }

    //增加该商品被选中的次数
    public void addSelectedCount() {
        selectedCount++;
    }

    //减少该商品被选中的次数
    public void subSelectedCount() {
        if (selectedCount > 0) {
            selectedCount--;
        }
    }

    //获取该商品的id
    public int getId() {
        return id;
    }

    //获取该商品的名字
    public String getName() {
        return name;
    }

    //获取该商品的价格
    public double getPrice() {
        return price;
    }

    //获取该商品的图片
    public int getPicture() {
        return picture;
    }

    //获取该商品的月销量
    public int getSale() {
        return sale;
    }

    //获取该商品的店铺名
    public String getShopName() {
        return shopName;
    }

    //获取该商品的详情
    public String getDetail() {
        return detail;
    }

    //获取是否显示减去按钮
    public boolean getIsShowSubBtn() {
        return isShowSubBtn;
    }

    //设置是否显示减去按钮
    public void setIsShowSubBtn(boolean isShowSubBtn) {
        this.isShowSubBtn = isShowSubBtn;
    }

    @Override
    public String toString() {
        return "Product[ name=" + name + ",price=" + price + ",picture=" + picture
                + ",sale=" + sale + ",shopName=" + shopName + ",detail=" + detail + ",selectedCount=" + selectedCount+" ]";
    }

    //以下是实现Parcelable自动生成的内容
    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        picture = in.readInt();
        sale = in.readInt();
        shopName = in.readString();
        detail = in.readString();
        selectedCount = in.readInt();
        isShowSubBtn = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(picture);
        dest.writeInt(sale);
        dest.writeString(shopName);
        dest.writeString(detail);
        dest.writeInt(selectedCount);
        dest.writeByte((byte) (isShowSubBtn ? 1 : 0));
    }
}
