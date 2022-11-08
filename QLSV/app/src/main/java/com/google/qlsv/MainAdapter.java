package com.google.qlsv;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
        holder.MaSV.setText(model.getMaSV());
        holder.Ten.setText(model.getTen());
        holder.Gmail.setText(model.getGmail());

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_update))
                        .setExpanded(true,1288)
                        .create();

                View view=dialogPlus.getHolderView();
                EditText MaSV=view.findViewById(R.id.edtmasv);
                EditText Ten=view.findViewById(R.id.edtten);
                EditText Gmail=view.findViewById(R.id.edtgmail);
                EditText Surl =view.findViewById(R.id.edtimageurl);

                Button btnUpdate =view.findViewById(R.id.btnupdate);

                MaSV.setText(model.getMaSV());
                Ten.setText(model.getTen());
                Gmail.setText(model.getGmail());
                Surl.setText(model.getSurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map =new HashMap<>();
                        map.put("MaSV",MaSV.getText().toString());
                        map.put("Ten",Ten.getText().toString());
                        map.put("Gmail",Gmail.getText().toString());
                        map.put("Surl",Surl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("SinhVien")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.MaSV.getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.MaSV.getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
                        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(holder.Ten.getContext());
                                builder.setTitle("Are you sure");
                                builder.setMessage("Dữ liệu không thể trở lại");
                                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("SinhVien")
                                                .child(getRef(position).getKey()).removeValue();
                                    }
                                });
                                builder.setNegativeButton("Cancelled", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(holder.MaSV.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.show();



                            }
                        });











    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView MaSV,Ten,Gmail;
        Button btnEdit,btnDelete;




        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(CircleImageView) itemView.findViewById(R.id.img1);
            MaSV=(TextView) itemView.findViewById(R.id.masvtext);
            Ten=(TextView) itemView.findViewById(R.id.tentext);
            Gmail=(TextView) itemView.findViewById(R.id.gmailtext);

            btnEdit=(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete=(Button) itemView.findViewById(R.id.btnDelete);


        }
    }
}

