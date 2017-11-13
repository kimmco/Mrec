package com.cokimutai.mrec;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cokimutai on 1/9/2017.
 */
public class RecyclerAdapter extends
        RecyclerView.Adapter<RecyclerAdapter.ListViewHolder> {

    Context context;
    List<SuppliaData>dataList = new ArrayList<>();
    LayoutInflater inflater;
    Listner listner;

    public RecyclerAdapter(Context context, List<SuppliaData>dataList1){

        this.context = context;
        this.dataList = dataList1;
        this.listner = (Listner)context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup group, int viewType){
        View convertView = inflater.inflate(
                R.layout.listrow, group, false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return viewHolder;
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        TextView supplia_name, supplierId;
        ImageView del_Image;

        public ListViewHolder(View itemView){
            super(itemView);

            supplia_name= (TextView)itemView.findViewById(R.id.supp_name);
            supplierId= (TextView)itemView.findViewById(R.id.supply_Id);
            del_Image= (ImageView)itemView.findViewById(R.id.delete_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String suppliaIdValue =
                            supplierId.getText().toString();
                    final Intent myIntent =
                            new Intent(context, Intake_Daily.class);
                    myIntent.putExtra("suppliaId", suppliaIdValue);
                    context.startActivity(myIntent);
                    try {
                        finalize();
                    } catch (Throwable throwable) {


                    }
                }
            });

        }
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder,int position){
        holder.del_Image.setTag(position);
        holder.supplia_name.setText(dataList.get(position).fname +
        " " + dataList.get(position).lname);

        holder.supplierId.setText(dataList.get(position).getSuppliaId());

        holder.del_Image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String suppliaIdValue =
                                holder.supplierId.getText().toString();

                        final Intent myIntent = new Intent(context, StoreView.class);
                        myIntent.putExtra("suppliaId", suppliaIdValue);
                        context.startActivity(myIntent);
                        try {
                            finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }
}
