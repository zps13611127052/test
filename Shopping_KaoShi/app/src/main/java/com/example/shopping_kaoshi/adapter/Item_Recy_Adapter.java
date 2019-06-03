package com.example.shopping_kaoshi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shopping_kaoshi.MainActivity;
import com.example.shopping_kaoshi.R;
import com.example.shopping_kaoshi.bean.Item_Bean;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 张十八 on 2019/5/24.
 */

public class Item_Recy_Adapter extends RecyclerView.Adapter {
    private final MainActivity mMainActivity;
    public ArrayList<Item_Bean> mBeans;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;


    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
    }

    public Item_Recy_Adapter(MainActivity mainActivity, ArrayList<Item_Bean> beans) {
        mMainActivity = mainActivity;
        mBeans = beans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mMainActivity).inflate(R.layout.item_layout, null);
        viewholder viewholder = new viewholder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final viewholder viewholder = (Item_Recy_Adapter.viewholder) holder;
        final Item_Bean bean = mBeans.get(position);

        viewholder.mGeshu.setText(mBeans.get(position).getCount()+"");
        viewholder.mCheck_id.setChecked(mBeans.get(position).isIstrue());
        viewholder.mName.setText(mBeans.get(position).getName());
        viewholder.mPrice.setText(mBeans.get(position).getPrice()+"");
        //点击事件
        viewholder.mCheck_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setIstrue(((CheckBox)v).isChecked());
                checkInterface.checkGroup(position,((CheckBox)v).isChecked());
            }
        });

        //减号的点击事件
        viewholder.mSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把下标，还有个数，以及是否选中的状态发过去
                modifyCountInterface.doDecrease(position,viewholder.mGeshu,viewholder.mCheck_id.isChecked());
            }
        });

        //加号的点击事件。
        viewholder.mInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position,viewholder.mGeshu,viewholder.mCheck_id.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        private final CheckBox mCheck_id;
        private final TextView mName;
        private final TextView mPrice;
        private final TextView mSub;
        private final TextView mGeshu;
        private final TextView mInter;

        public viewholder(View itemView) {
            super(itemView);
            mCheck_id = itemView.findViewById(R.id.check_id);
            mName = itemView.findViewById(R.id.name);
            mPrice = itemView.findViewById(R.id.price);
            mSub = itemView.findViewById(R.id.sub);
            mGeshu = itemView.findViewById(R.id.geshu);
            mInter = itemView.findViewById(R.id.inter);
        }
    }
}
