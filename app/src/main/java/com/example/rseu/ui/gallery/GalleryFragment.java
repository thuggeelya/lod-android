package com.example.rseu.ui.gallery;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.example.rseu.AchievementsActivity;
import com.example.rseu.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GalleryFragment extends Fragment {

    private final String[] categories = {"Научная деятельность", "Учебная деятельность", "Спортивная деятельность", "Культурная деятельность"};
    private final String[] types1 = {"Интеллектуальная собственность", "Участие в конференциях", "Конкурсы, выставки, кейсы", "Научные стажировки", "Исследование по программам и грантам"};
    private final String[] types2 = {"Победитель или призер олимпиады", "Победитель или призер конкурса, направленного на выявление учебных достижений студентов", "Наличие сертификатов на знание иностранных языков", "Результаты академического обмена"};
    private final String[] types3 = {"Разряд", "Олимпийские игры", "Чемпионат мира/Европы/России, общий зачет кубка мира/Европы/России", "Кубок мира/Европы/России", "Первенство мира/Европы/России", "Универсиады и спартакиады", "ГТО"};
    private final String[] types4 = {"Победы в творческих конкурсах", "Участие в культурно-массовых мероприятиях", "Результаты творческой дестельности"};
    @SuppressLint("StaticFieldLeak")
    private static Spinner s1;
    @SuppressLint("StaticFieldLeak")
    private static Spinner s2;
    private static Dialog dialog;

    @SuppressLint("StaticFieldLeak")
    public static Button OKDialogButton;

    private int nextAchieveNumber = 0;

    private final LinkedHashMap<String,String> allAchievements = new LinkedHashMap<>();
    public static ArrayList<LinkedHashMap<String,String>> achievements= new ArrayList<>();

    public static ArrayList<String> project_names = new ArrayList<>();
    public static ArrayList<String> saved_names = new ArrayList<>();
    public static ArrayList<String> my_categories = new ArrayList<>();

    public static int buttonNumber;

    public static SharedPreferences sPref;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    void saveText(String savedText, String put, Context context) {
        sPref = context.getSharedPreferences("projects", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(savedText, put);
        ed.apply();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        context = root.getContext();

        Button a1 = root.findViewById(R.id.achieve_1);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNumber = 0;
                Intent intent = new Intent(requireContext(), AchievementsActivity.class);
                startActivity(intent);
            }
        });
        Button a2 = root.findViewById(R.id.achieve_2);
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNumber = 1;
                Intent intent = new Intent(requireContext(), AchievementsActivity.class);
                startActivity(intent);
            }
        });
        Button a3 = root.findViewById(R.id.achieve_3);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNumber = 2;
                Intent intent = new Intent(requireContext(), AchievementsActivity.class);
                startActivity(intent);
            }
        });
        Button a4 = root.findViewById(R.id.achieve_4);
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNumber = 3;
                Intent intent = new Intent(requireContext(), AchievementsActivity.class);
                startActivity(intent);
            }
        });

        a1.setVisibility(View.INVISIBLE);
        a2.setVisibility(View.INVISIBLE);
        a3.setVisibility(View.INVISIBLE);
        a4.setVisibility(View.INVISIBLE);

        final ArrayList<Button> achieves = new ArrayList<>();
        achieves.add(a1);
        achieves.add(a2);
        achieves.add(a3);
        achieves.add(a4);


        final Button loadButton1 = root.findViewById(R.id.load_button1);
        loadButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.load_dialog_layout);
                dialog.setTitle("Новое достижение");

                //isLoaded = dialog.findViewById(R.id.isLoaded);
                //isLoaded.setVisibility(View.INVISIBLE);

                OKDialogButton = dialog.findViewById(R.id.ok_dialog_button);
                //OKDialogButton.setEnabled(false);

                Spinner categorySpinner = dialog.findViewById(R.id.load_category_spinner);

                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, categories);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                categorySpinner.setAdapter(adapter1);
                // заголовок
                categorySpinner.setPrompt("Категории деятельности");
                // выделяем элемент
                categorySpinner.setSelection(0);
                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                        new CountDownTimer(500, 500) {
                            @Override
                            public void onTick(long millisUntilFinished) {}
                            @Override
                            public void onFinish() {
                                CountDown.start();
                            }
                        }.start();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                s1 = categorySpinner;

                Button cancelDialogButton = dialog.findViewById(R.id.cancel_dialog_button);
                cancelDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                final String[] dateCalendar = new String[1];
                final CalendarView calendarView = dialog.findViewById(R.id.calendarView);
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year,
                                                    int month, int dayOfMonth) {
                        dateCalendar[0] = dayOfMonth + "." + (month + 1) + "." + year;
                    }
                });

                final EditText achieveName = dialog.findViewById(R.id.achieve_name);

                OKDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView tv = root.findViewById(R.id.textView2);
                        tv.setVisibility(View.INVISIBLE);

                        String name = achieveName.getText().toString();
                        String category = s1.getSelectedItem().toString();
                        String type = s2.getSelectedItem().toString();
                        String date = dateCalendar[0];

                        allAchievements.put("Наименование", name);
                        allAchievements.put("Категория", category);
                        allAchievements.put("Тип", type);
                        allAchievements.put("Дата", date);
                        achievements.add(allAchievements);

                        String achieveDescription = name + "\nКатегория: " + category + "\nПодробности: " + type + "\n" + date;
                        project_names.add(name);
                        my_categories.add(type);

                        achieves.get(nextAchieveNumber).setText(achieveDescription);
                        achieves.get(nextAchieveNumber).setVisibility(View.VISIBLE);

                        if (nextAchieveNumber != 3)
                            nextAchieveNumber += 1;

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return root;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private final CountDownTimer CountDown = new CountDownTimer(500, 250) {

        public void onTick(long millisUntilFinished) {
            // общее условие
            ArrayAdapter adapter2 = null;
            if (s1.getSelectedItem().toString().equals("Научная деятельность")) {
                adapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, types1);
            }
            else if (s1.getSelectedItem().toString().equals("Учебная деятельность")) {
                adapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, types2);
            }
            else if (s1.getSelectedItem().toString().equals("Спортивная деятельность")) {
                adapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, types3);
            }
            else if (s1.getSelectedItem().toString().equals("Культурная деятельность")) {
                adapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, types4);
            }

            assert adapter2 != null;
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s2 = dialog.findViewById(R.id.load_type_spinner);
            s2.setAdapter(adapter2);
            // заголовок3
            s2.setPrompt(s1.getSelectedItem().toString());
            // выделяем элемент2
            s2.setSelection(1);
            // устанавливаем обработчик нажатия3
            s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // показываем позицию нажатого элемента
                    // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        public void onFinish() {
            this.cancel();
        }
    };
}