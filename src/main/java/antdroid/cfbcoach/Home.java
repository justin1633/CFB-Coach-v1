package antdroid.cfbcoach;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import 	javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageLogo = (ImageView) findViewById(R.id.imageLogo);
        imageLogo.setImageResource(R.drawable.main_menu_logo);

/*        Button newGameButton = (Button) findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Home.this, MainActivity.class);
                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_DYNASTY");
                Home.this.startActivity(myIntent);
            }
        });*/

/*        Button newGameButton = (Button) findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            customLeague();
            }
            });*/

        Button newCareerButton = (Button) findViewById(R.id.buttonCareer);
        newCareerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Home.this, MainActivity.class);
                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_CAREER");
                Home.this.startActivity(myIntent);
            }
        });

        Button loadGameButton = (Button) findViewById(R.id.buttonLoadGame);
        loadGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                loadLeague();
            }
        });

        Button deleteGameButton = (Button) findViewById(R.id.buttonDeleteSave);
        deleteGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                deleteSave();
            }
        });

        Button tutorialButton = (Button) findViewById(R.id.buttonTutorial);
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(Home.this, TutorialActivity.class);
                Home.this.startActivity(myIntent);
            }
        });

        Button subredditButton = (Button) findViewById(R.id.buttonSubreddit);
        subredditButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://m.reddit.com/r/FootballCoach"));
                startActivity(intent);
            }
        });


        Button antdroidButton = (Button) findViewById(R.id.buttonAntdroid);
        antdroidButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.Antdroid.net"));
                startActivity(intent);
            }
        });

        Button githubButton = (Button) findViewById(R.id.buttonGitHub);
        githubButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/minotauri/CFB-Coach"));
                startActivity(intent);
            }
        });

        Button proButton = (Button) findViewById(R.id.buttonProFootballCoach);
        proButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.achijones.profootballcoach")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.achijones.profootballcoach")));
                }
            }
        });

        Button CollegeHoopsCoachButton = (Button) findViewById(R.id.buttonCollegeHoopsCoach);
        CollegeHoopsCoachButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=io.coachapps.collegebasketballcoach")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=io.coachapps.collegebasketballcoach")));
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Save League, show dialog to choose which save file to save onto.
     */
    private void loadLeague() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose File to Load:");
        final String[] fileInfos = getSaveFileInfos();
        SaveFilesListArrayAdapter saveFilesAdapter = new SaveFilesListArrayAdapter(this, fileInfos);
        builder.setAdapter(saveFilesAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (!fileInfos[item].equals("EMPTY")) {
                    Intent myIntent = new Intent(Home.this, MainActivity.class);
                    myIntent.putExtra("SAVE_FILE", "saveFile" + item + ".cfb");
                    Home.this.startActivity(myIntent);
                } else {
                    Toast.makeText(Home.this, "Cannot load empty file!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Delete Save
     */
    private void deleteSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose File to Delete:");
        final String[] fileInfos = getSaveFileInfos();
        SaveFilesListArrayAdapter saveFilesAdapter = new SaveFilesListArrayAdapter(this, fileInfos);
        builder.setAdapter(saveFilesAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (!fileInfos[item].equals("EMPTY")) {
                    deleteFile("saveFile" + item + ".cfb");
                } else {
                    Toast.makeText(Home.this, "Cannot load delete file!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Get info of the 10 save files for printing in the save file list
     */
    private String[] getSaveFileInfos() {
        String[] infos = new String[10];
        String fileInfo;
        File saveFile;
        File extFile;
        for (int i = 0; i < 10; ++i) {
            saveFile = new File(getFilesDir(), "saveFile" + i + ".cfb");
            if (saveFile.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile));
                    fileInfo = bufferedReader.readLine();
                    infos[i] = fileInfo.substring(0, fileInfo.length() - 1); //gets rid of % at end
                } catch (FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file");
                } catch (IOException ex) {
                    System.out.println(
                            "Error reading file");
                } catch (NullPointerException ex) {
                    System.out.println(
                            "Null pointer exception!");
                }
            } else {
                infos[i] = "EMPTY";
            }
        }
        return infos;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    /* Creates external Save directory */

    public File getExtSaveDir(Context context, String cfbCoach) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), cfbCoach);
        if (!file.mkdirs()) {
            Log.e(cfbCoach, "Directory not created");
        }
        return file;
    }

   /* private void customLeague() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Universe File URL:");
        final EditText input = new EditText(this);
        input.setInputType(1);
        builder.setView(input);
        builder.setPositiveButton("Load", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(Home.this, MainActivity.class);

                try {
                    URL url = new URL(input.getText().toString());
                    File conferences = new File(getFilesDir(), "conferences.txt");
                    File teams = new File(getFilesDir(), "teams.txt");
                    URLConnection conn = url.openConnection();
                    // Always wrap FileReader in BufferedReader.
                    String line = null;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    //First ignore the save file info
                    line = bufferedReader.readLine();
                    //Next get league history
                    while ((line = bufferedReader.readLine()) != null && !line.equals("[END_CONFERENCES]")) {
                        sb.append(line);
                    }
                    sb.append("END_CONFERENCES");

                    // Actually write to the file
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(conferences), "utf-8"))) {
                        writer.write(sb.toString());
                    } catch (Exception e) {
                    }
                    StringBuilder sb1 = new StringBuilder();

                    //Next get heismans
                    while ((line = bufferedReader.readLine()) != null && !line.equals("END_TEAMS")) {
                        sb1.append(line);
                    }
                    sb1.append("END_TEAMS");
                    // Actually write to the file
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(teams), "utf-8"))) {
                        writer.write(sb1.toString());
                    } catch (Exception e) {
                    }
                    // Always close files.
                    bufferedReader.close();
                    myIntent.putExtra("SAVE_FILE","NEW_LEAGUE_CAREER-CUSTOM");
                    Home.this.startActivity(myIntent);
                } catch (Exception e) {
                    Toast.makeText(Home.this, "Error! Bad URL or unable to read file.", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNeutralButton("How To Guide", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setData(Uri.parse("https://m.reddit.com/r/FootballCoach/wiki/rosters"));
                Home.this.startActivity(intent);
            }
        });
        builder.show();
    }

    private void customFileBuilder() {

    }*/
}
