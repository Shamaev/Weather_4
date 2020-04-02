package com.geekbrains.cases.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.cases.App;
import com.geekbrains.cases.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorksRecyclerAdapter extends RecyclerView.Adapter<WorksRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private EducationSource dataSource;
    private EducationSource educationSource;
    // Позиция в списке, на которой было нажато меню
    private long menuPosition;
    private Context context;

    public WorksRecyclerAdapter(EducationSource dataSource, Activity activity){
        this.dataSource = dataSource;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final List<Cases> list = dataSource.getCases();
        final Cases cases = list.get(position);
        holder.textView.setText(cases.textWork);
        if (cases.checkWork == 1) {
            holder.checkBox.setChecked(true);
            holder.textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.checkBox.setChecked(false);
            holder.textView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    RemoveDB(position, 1, cases.textWork);
                    holder.textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    RemoveDB(position, 0, cases.textWork);
                    holder.textView.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                }
            }
        });

        // Тут определяем, какой пункт меню был нажат
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuPosition = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Информация")
                        .setMessage("Дата: " + cases.data +"\nВремя: " + cases.time + "\nДело: " + cases.textWork)
                        .setCancelable(false)
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Закрываем окно
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });

        // Регистрируем контекстное меню
        if (activity != null){
            activity.registerForContextMenu(holder.cardView);
        }
    }

    @Override
    public int getItemCount() {
        return (int) dataSource.getCountCases();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        View cardView;

        ViewHolder(final View view) {
            super(view);
            cardView = itemView;
            checkBox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.textWork);
        }
    }

    public void RemoveDB (int position, int check, String text) {
        EducationDao educationDao = App
                .getInstance()
                .getEducationDao();
        educationSource = new EducationSource(educationDao);

        Cases oldListWork = educationSource
                .getCases()
                .get(position);
        oldListWork.textWork = text;
        if (check == 1) {
            oldListWork.checkWork = 1;
        } else {
            oldListWork.checkWork =0;
        }

        educationSource.updateStudent(oldListWork);

    }
}
