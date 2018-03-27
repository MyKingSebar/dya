package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import butterknife.ButterKnife;

/**
 * Created by qy on 2018/3/26.
 */

public abstract class NewBaseAdapter<T> extends BaseAdapter {

    protected Context context;

    protected T mData;

    public NewBaseAdapter(Context context){
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseHolder holder=null;

        if (convertView == null) {

            holder = getHolder(context);

            convertView = View.inflate(context, getView(), null);

            ButterKnife.bind(holder, convertView);//用butterKnife绑定

            convertView.setTag(holder);

        } else {

            holder = (BaseHolder) convertView.getTag();

        }

        holder.setData(position,mData);//将数据传给holder

        return convertView;

    }

    /**
     * 返回对应的holder类
     * @param context 引用
     * @return 返回对应的holder子类，需要继承自BaseHolder
     */
    protected abstract BaseHolder getHolder(Context context);

    /**
     *
     * @return 返回布局的资源文件id
     */
    protected abstract int getView();


    /**
     * 设置数据
     * @param data 对应的数据
     */
    public void setData(T data){
        mData=data;

    }


    public abstract class BaseHolder<T>
    {

        /**
         * 设置要显示的数据
         * @param data 数据，转换成对应的数据对象
         * @param position 当前item的位置
         */
        public abstract void setData(int position,T data);

    }
}