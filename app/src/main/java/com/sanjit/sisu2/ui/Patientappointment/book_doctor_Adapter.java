package com.sanjit.sisu2.ui.Patientappointment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjit.sisu2.R;
import com.sanjit.sisu2.ui.Patientappointment.book_doctor_recyclerViewInterface;
import java.util.ArrayList;
import java.util.Map;

public class book_doctor_Adapter extends RecyclerView.Adapter<book_doctor_Adapter.book_doctor_ViewHolder>{

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Context context;
    private final ArrayList<book_doctor_model> book_doctor_arr;
    private final book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface;

    book_doctor_Adapter(Context context,
                        ArrayList<book_doctor_model> book_doctor_arr,
                        book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface)
    {
        this.context = context;
        this.book_doctor_arr = book_doctor_arr;
        this.book_doctor_recyclerViewInterface = book_doctor_recyclerViewInterface;
    }

    @NonNull
    @Override
    public book_doctor_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(context).inflate(R.layout.doctor_card_view, parent, false);
        book_doctor_ViewHolder holder = new book_doctor_ViewHolder(inflater, book_doctor_recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull book_doctor_ViewHolder holder, int position) {

        holder.name.setText(book_doctor_arr.get(position).getName());
        holder.phone.setText(book_doctor_arr.get(position).getPhone());
        holder.photo.setImageResource(R.drawable.facebook);
        if(book_doctor_arr.get(position).getBooked())
        {
            holder.book.setTextColor(Color.parseColor("#6fc276"));
            holder.book.setText("Booked");
            holder.book.setEnabled(false);
        }
        else
        {
            holder.book.setText("Book");
            holder.book.setEnabled(true);
        }

    }

    @Override
    public int getItemCount() {
        return book_doctor_arr.size();
    }

    public static class book_doctor_ViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone;
        ImageView photo;
        Button book;

            public book_doctor_ViewHolder(View itemView, book_doctor_recyclerViewInterface book_doctor_recyclerViewInterface) {

                super(itemView);
                name = itemView.findViewById(R.id.patient_name);
                phone = itemView.findViewById(R.id.patient_phone);
                photo = itemView.findViewById(R.id.patient_image);
                book = itemView.findViewById(R.id.book_button);

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //make sure that the interface is not null
                            if (book_doctor_recyclerViewInterface != null) {
                                int position = getAbsoluteAdapterPosition();
                                //check if the position is valid
                                if (position != RecyclerView.NO_POSITION) {
                                    book_doctor_recyclerViewInterface.onDoctorClick(position);
                                }
                            }
                        }
                    });

                    book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //make sure that the interface is not null
                            if (book_doctor_recyclerViewInterface != null) {
                                int position = getAbsoluteAdapterPosition();
                                //check if the position is valid
                                if (position != RecyclerView.NO_POSITION) {
                                    book_doctor_recyclerViewInterface.onBookClick(position, book);
                                }
                            }
                        }
                    });

            }
    }
}