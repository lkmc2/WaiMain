package linchange.example.com.waimain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.ShoppingActivity;
import linchange.example.com.waimain.entity.Product;

//商品列表适配器
public class ProductListAdapter extends BaseAdapter {
    private Context mContext; //页面布局加载器

    private List<Product> productsData; //商品内容数据源
    private ArrayList<Product> selectedProducts = new ArrayList<Product>(); //被选择的的商品数据

    private int totalSelectedCount = 0; //当前选择商品总数
    private int totalPrice = 0; //当前选择商品总价

    public ProductListAdapter(Context context, List<Product> data) {
        mContext = context;
        productsData = data;
    }

    //返回适配器内子项的数量
    @Override
    public int getCount() {
        return productsData.size();
    }

    //返回适配器内的某个子项
    @Override
    public Object getItem(int position) {
        return productsData.get(position);
    }

    //返回适配器内的某个子项的id号
    @Override
    public long getItemId(int position) {
        return position;
    }

    //每个子view生成都会调用的界面生成方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null; //声明控件暂存器

        if (convertView == null) { //如果内容界面为空
            viewHolder = new ViewHolder(); //新建控件暂存器
            //加载内容界面
            convertView = LayoutInflater.from(mContext).inflate(R.layout.product_listview_item, null);

            //绑定控件
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.iv_product_picture);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_product_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_product_pirce);
            viewHolder.sale = (TextView) convertView.findViewById(R.id.tv_sale_count);
            viewHolder.shopName = (TextView) convertView.findViewById(R.id.tv_product_shop_name);
            viewHolder.detail = (TextView) convertView.findViewById(R.id.tv_product_detail);
            viewHolder.selectedCount = (TextView) convertView.findViewById(R.id.tv_selected_count);
            viewHolder.addProduct = (ImageButton) convertView.findViewById(R.id.btn_add_product);
            viewHolder.subProduct = (ImageButton) convertView.findViewById(R.id.btn_sub_product);

            convertView.setTag(viewHolder); //将控件暂存器放到内容界面中
        } else { //内容界面不为空
            viewHolder = (ViewHolder) convertView.getTag(); //从内容界面提取出控件暂存器
        }

        Product product = productsData.get(position); //获取当前位置的商品

        initListItem(viewHolder, product); //初始化列表界面内容
        initButtonEvent(viewHolder, product); //初始化按钮的点击事件

        return convertView; //返回主界面
    }

    /**
     * 初始化列表界面内容
     * @param viewHolder 控件暂存器
     * @param product 当前位置的产品
     */
    private void initListItem(final ViewHolder viewHolder, final Product product) {
        viewHolder.picture.setImageResource(product.getPicture()); //设置商品图片
        viewHolder.name.setText(product.getName()); //设置商品名
        viewHolder.price.setText(String.valueOf("￥" + product.getPrice())); //设置商品价格
        viewHolder.sale.setText(String.valueOf("月售:" + product.getSale())); //设置商品月销售量
        viewHolder.shopName.setText(product.getShopName()); //设置商家名称
        viewHolder.detail.setText(product.getDetail()); //设置商品简介
    }

    /**
     * 初始化按钮的点击事件
     * @param viewHolder 控件暂存器
     * @param product 当前位置的产品
     */
    private void initButtonEvent(final ViewHolder viewHolder, final Product product) {
        //给添加商品按钮添加点击事件
        viewHolder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalSelectedCount++; //被选择商品总数加1
                totalPrice += product.getPrice(); //被选择商品总价加上该商品的价格

                product.addSelectedCount(); //商品本身的数量加1

                if (!selectedProducts.contains(product)) { //如果被选择商品列表还没有该商品
                    selectedProducts.add(product); //将该商品加到被选择列表中
                }

                product.setIsShowSubBtn(true); //设置商品对应的减去按钮消失

                //在被选择商品数标签上设置被选择商品数
                viewHolder.selectedCount.setText(String.valueOf(product.getSelectedCount()));

                //将被选择的商品总数和总价设置回购物界面
                ((ShoppingActivity)mContext).setSelectedCountAndPrice(totalSelectedCount, totalPrice);
                //将被选择的商品列表设置回购物界面
                ((ShoppingActivity)mContext).setSelectedProducts(selectedProducts);
            }
        });

        //给减去商品按钮添加点击事件
        viewHolder.subProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelectedCount - 1 >= 0) { //当被选择总数减1不为0时
                    totalSelectedCount--; //被选择商品总数减1
                    totalPrice -= product.getPrice(); //被选择商品总价减去该商品的价格

                    product.subSelectedCount();  //商品本身的数量减1

                    if (product.getSelectedCount() == 0) { //如果被选择商品的数量为0
                        product.setIsShowSubBtn(false); //设置该商品对应的减去按钮消失
                        selectedProducts.remove(product); //从被选择商品列表中移除该商品
                   }

                    //在被选择商品数标签上设置被选择商品数
                    viewHolder.selectedCount.setText(String.valueOf(product.getSelectedCount()));

                    //将被选择的商品总数和总价设置回购物界面
                    ((ShoppingActivity)mContext).setSelectedCountAndPrice(totalSelectedCount, totalPrice);
                    //将被选择的商品列表设置回购物界面
                    ((ShoppingActivity)mContext).setSelectedProducts(selectedProducts);
                }
            }
        });

        //设置减去按钮的与被选择商品数量标签的可见性
        setSubButtonAndSelectCount(viewHolder, product);
    }

    /**
     * 设置减去按钮的与被选择商品数量标签的可见性
     * @param viewHolder 控件暂存器
     * @param product 当前位置的产品
     */
    private void setSubButtonAndSelectCount(final ViewHolder viewHolder, Product product) {
        boolean show = product.getIsShowSubBtn(); //从Map中获取是否需要展示减去按钮

        viewHolder.subProduct.setVisibility(show ? View.VISIBLE : View.INVISIBLE); //设置减去商品按钮是否可见
        viewHolder.selectedCount.setVisibility(show ? View.VISIBLE : View.INVISIBLE); //设置被选择商品数标签是否可见

        if (show) { //需要展示减去按钮
            //在被选择商品数标签上设置被选择商品数
            viewHolder.selectedCount.setText(String.valueOf(product.getSelectedCount()));
        }
    }

    //设置全部商品数据
    public void setSelectedProducts(ArrayList<Product> selectedProducts) {
        this.selectedProducts = selectedProducts; //设置被选择的商品
        notifyDataSetChanged(); //通知系统数据改变
    }

    //控件暂存器
    static class ViewHolder {
        ImageView picture; //商品图片
        TextView name; //商品名
        TextView price; //商品价格
        TextView sale; //月销量
        TextView shopName; //商店名
        TextView detail; //商品细节
        TextView selectedCount; //选择商品的数量
        ImageButton addProduct; //添加商品按钮
        ImageButton subProduct; //减去商品按钮
    }

}
