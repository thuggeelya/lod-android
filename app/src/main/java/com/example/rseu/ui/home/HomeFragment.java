package com.example.rseu.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rseu.Login;
import com.example.rseu.R;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private static final String[] categoryArray = {"Все мероприятия", "Гранты, договорные работы", "Конкурс научных проектов, разработок", "Конференции, выставки, форумы, лекции", "От администрации", "От работодателей"};
    private static final String[] eventList = {
            "Современные тенденции развития психологической науки и практики",
            "Проблемы и перспективы развития бизнеса в России в период пандемии (постпандемии)",
            "Лучшая выпускная квалификационная работа управленческого профиля",
            "Дизайн в современном контексте информационных технологий",
            "Ростовская областная научная конференция",
            "Международная научно-практическая конференция \"Право. Общество. Государство: история и современность\"",
            "Всероссийская научно-практическая конференция Law and society (Право и общество)",
            "16-я Международная научно-практическая конференция ASECU",
            "VIII Международная научно-практическая конференция \"Актуальные вопросы современного образования\"",
            "Международная научно-практическая конференция \"Физико-математические и технические науки как фундамент становления постиндустриального общества\"",
            "Межкафедральный семинар Концептуальный аппарат теорий постсекулярного общества"
    };

    public Elements title;

    private static final HashMap<String, ArrayList<String>> event_display = new HashMap<>();

    private static String url = "https://rsue.ru/nauka/nauchnye-meropriyatiya/";

    private ListView lv;
    @SuppressLint("StaticFieldLeak")
    private static Spinner eventSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        View navFragment = inflater.inflate(R.layout.nav_header_start_page, container, false);
        String userName = Login.userName;
        TextView userText = navFragment.findViewById(R.id.user_name_id);

        userText.setText(userName);

        eventSpinner = root.findViewById(R.id.all_events_spinner);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, categoryArray);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);
        // заголовок
        eventSpinner.setPrompt("Title");
        // выделяем элемент
        eventSpinner.setSelection(0);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                // адаптер
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
                // пока ничего
            }
        });

        ListView listView = root.findViewById(R.id.events_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_list_item_1, eventList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog1 = new Dialog(root.getContext());
                dialog1.setContentView(R.layout.visit_dialog);
                dialog1.show();

                Button ok = dialog1.findViewById(R.id.visit_button);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });

            }
        });

        return root;
    }

    private CountDownTimer CountDown = new CountDownTimer(500, 250) {

        public void onTick(long millisUntilFinished) {
            // общее условие
            if (eventSpinner.getSelectedItem().toString().equals("Все мероприятия")) {
                // показать события по выбранной тематике
                ArrayList<String> eventList = new ArrayList<>();
                eventList.add("A\n01.12.2020");
                eventList.add("B\n03.12.2020");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, eventList);
            }
        }
        public void onFinish() {
            this.cancel();
        }
    };
}