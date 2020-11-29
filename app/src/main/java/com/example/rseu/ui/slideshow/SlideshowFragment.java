package com.example.rseu.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rseu.Login;
import com.example.rseu.R;
import com.example.rseu.Team;
import com.example.rseu.TeamActivity;
import com.example.rseu.ui.gallery.GalleryFragment;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static Button projectTeam;
    @SuppressLint("StaticFieldLeak")
    private static Spinner s;
    @SuppressLint("StaticFieldLeak")
    public static Button b;
    @SuppressLint("StaticFieldLeak")
    public static TextView tv;
    private static Dialog dialog;
    private static String title = "";
    private static String myField = "";
    public static String myName = "";

    public static ArrayList<Team> teams = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        final Spinner projects = root.findViewById(R.id.projects_spinner);

        final ArrayList<String> projectsList = new ArrayList<>(GalleryFragment.project_names);

        projectTeam = root.findViewById(R.id.team_project);
        projectTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), TeamActivity.class);
                if (teams.isEmpty()) {
                    Toast.makeText(requireContext(), "Команды еще нет", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, projectsList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        projects.setAdapter(adapter1);
        // заголовок
        projects.setPrompt("Мои проекты");

        final ArrayList<String> types = Login.types;
        final ArrayList<String> logins = Login.logins;
        final ArrayList<Long> ids = Login.ids;
        final ArrayList<String> my_categories = GalleryFragment.my_categories;

        projects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                String name = s.getSelectedItem().toString();
                String text = name + "\nИнформация о команде";

                projectTeam.setText(text);

                String selItem = projects.getItemAtPosition(position).toString();
                int pos1 = projectsList.indexOf(selItem);
                String type = my_categories.get(pos1);
                int pos2 = types.indexOf(type);

                tv.setText(type);

                title = logins.get(pos2);
                myField = my_categories.get(pos1);
                b.setText("Научный сотрудник \nНикнейм: " + title + "\nID: " + ids.get(pos2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s = projects;

        //ФОРМИРОВАНИЕ КОМАНД, ПОЗВАТЬ В КОМАНДУ____________________________________________________________________________________________________________________________________

        b = root.findViewById(R.id.users_recommend);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b.getText().equals("")) {
                    Toast.makeText(requireContext(), "Предложений еще нет.\nЗагрузите свои проекты!", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog = new Dialog(requireContext());
                    dialog.setContentView(R.layout.add_to_team_dialog);
                    dialog.setTitle(title);
                    dialog.show();

                    final Button add = dialog.findViewById(R.id.invite_button);
                    add.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            Team team = new Team();
                            myName = projects.getSelectedItem().toString();
                            team.setName(myName);
                            team.setField(myField);
                            ArrayList<String> users = new ArrayList<>();
                            users.add(Login.userName);
                            users.add(title);
                            team.setUsers(users);
                            teams.add(team);

                            add.setVisibility(View.INVISIBLE);
                            TextView added = dialog.findViewById(R.id.added);
                            added.setText("Пользователь " + title + " успешно добавлен в команду по направлению: " + team.getName());

                            b.setEnabled(false);
                        }
                    });
                }
            }
        });

        tv = root.findViewById(R.id.match);

        return root;
    }
}