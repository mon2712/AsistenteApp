package com.vane.montse.asistente;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private List<Alumno> mData;

    Icon_Manager icon_manager;

    public RecyclerViewAdapter(Context mContext, List<Alumno> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.student_container,parent,false);

        return new MyViewHolder(view);
    }

    public void updateData(List<Alumno> alumnos) {
        mData.clear();
        mData.addAll(alumnos);
        notifyDataSetChanged();
    }

    public void addItem(int position, Alumno alumno) {
        mData.add(position, alumno);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        icon_manager = new Icon_Manager();

        holder.tv_name.setText(mData.get(position).getNombre());
        holder.tv_lastName.setText(mData.get(position).getApellido());
        holder.tv_level.setText(mData.get(position).getNivel());
        holder.tv_time.setText(mData.get(position).getTiempo());
        holder.tv_timeReduced.setText(mData.get(position).getTiempoReducido());
        holder.tv_img.setTypeface(icon_manager.get_icons("fonts/trabajoterminalfonts.ttf", mContext));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_lastName;
        TextView tv_level;
        TextView tv_time;
        TextView tv_timeReduced;
        TextView tv_img;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.nameStudent);
            tv_lastName = (TextView) itemView.findViewById(R.id.lastName);
            tv_level = (TextView) itemView.findViewById(R.id.level);
            tv_time = (TextView) itemView.findViewById(R.id.time);
            tv_timeReduced = (TextView) itemView.findViewById(R.id.timeReduced);
            tv_img = (TextView) itemView.findViewById(R.id.iconImg);

        }

    }

}
