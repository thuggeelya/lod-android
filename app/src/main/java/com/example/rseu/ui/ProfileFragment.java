package com.example.rseu.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.rseu.Interests;
import com.example.rseu.Login;
import com.example.rseu.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private final String[] base = {"Астрология", "Биология", "Экономика", "Математика", "Литература", "Нейрология", "Механика", "Физика", "Химия", "Культурология",
                                    "Офтальмология", "Агрономия", "Лингвистика", "Наркология", "Онкология", "Радиохимия", "Статистика", "Флористика", "Цитология", "Психология"};

    private static Dialog dialog;
    private static int buttonNumber = 0;

    public static ArrayList<Interests> allInterests = new ArrayList<>();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.fragment_profile, container, false);


        final TextView chosenTags = root.findViewById(R.id.chosen_tags);
        chosenTags.setVisibility(View.INVISIBLE);

        final Button choose = root.findViewById(R.id.choose_tags_button);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.tags_dialog);
                dialog.setTitle("Интересы");

                final AutoCompleteTextView tags1 = dialog.findViewById(R.id.tags_textView1);
                ArrayAdapter<String> adapter1 =
                        new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, base);
                tags1.setAdapter(adapter1);

                final AutoCompleteTextView tags2 = dialog.findViewById(R.id.tags_textView2);
                ArrayAdapter<String> adapter2 =
                        new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, base);
                tags2.setAdapter(adapter2);

                final AutoCompleteTextView tags3 = dialog.findViewById(R.id.tags_textView3);
                ArrayAdapter<String> adapter3 =
                        new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, base);
                tags3.setAdapter(adapter3);

                final AutoCompleteTextView tags4 = dialog.findViewById(R.id.tags_textView4);
                ArrayAdapter<String> adapter4 =
                        new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, base);
                tags4.setAdapter(adapter4);

                final AutoCompleteTextView tags5 = dialog.findViewById(R.id.tags_textView5);
                ArrayAdapter<String> adapter5 =
                        new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, base);
                tags5.setAdapter(adapter5);

                final AutoCompleteTextView tags6 = dialog.findViewById(R.id.tags_textView6);
                ArrayAdapter<String> adapter6 =
                        new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, base);
                tags6.setAdapter(adapter6);

                final ArrayList<AutoCompleteTextView> tags = new ArrayList<>();

                final ConstraintLayout layout = dialog.findViewById(R.id.my_layout);

                for( int i = 0; i < layout.getChildCount(); i++ ){
                    if( layout.getChildAt( i ) instanceof AutoCompleteTextView)
                        tags.add( (AutoCompleteTextView) layout.getChildAt( i ) );
                }

                tags2.setVisibility(View.INVISIBLE);
                tags3.setVisibility(View.INVISIBLE);
                tags4.setVisibility(View.INVISIBLE);
                tags5.setVisibility(View.INVISIBLE);
                tags6.setVisibility(View.INVISIBLE);

                final ImageButton add = dialog.findViewById(R.id.add_tags_textView_button);

                 add.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         buttonNumber++;

                         if ((buttonNumber <= tags.size())) {
                             AutoCompleteTextView actv = tags.get(buttonNumber);
                             actv.setVisibility(View.VISIBLE);
                         } else {
                             add.setEnabled(false);
                         }
                     }
                 });

                Button confirm = dialog.findViewById(R.id.confirm_tags_button);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder text = new StringBuilder();

                        Interests interests = new Interests();
                        interests.setName(Login.userName);

                        ArrayList<String> myInterests = new ArrayList<>();

                        for( int i = 0; i < layout.getChildCount(); i++ ){
                            if( layout.getChildAt( i ) instanceof AutoCompleteTextView && layout.getChildAt( i ).getVisibility() == View.VISIBLE) {
                                text.append(tags.get(i).getEditableText().toString()).append(" / ");
                                myInterests.add(tags.get(i).getEditableText().toString());
                            }
                        }

                        if (myInterests.isEmpty()) {
                            myInterests.add("Пользователь еще не указал свои интересы");
                        }

                        interests.setInterests(myInterests);
                        allInterests.add(interests);

                        choose.setVisibility(View.INVISIBLE);

                        chosenTags.setVisibility(View.VISIBLE);
                        chosenTags.setText(text);

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return root;
    }
}