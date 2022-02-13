package de.androidcrypto.readwritecsvfiles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // program is based on articles from mkyong.com:
    // https://mkyong.com/java/how-to-export-data-to-csv-file-java/
    // https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
    // minimum sdk ist 26

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWrite = (Button) findViewById(R.id.btnWriteCsv);
        Button btnRead = (Button) findViewById(R.id.btnReadCsv);
        EditText etContent = (EditText) findViewById(R.id.etContent);

        final String csvFilename = "csv1.txt";

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = getFilesDir().getAbsolutePath();
                String csvFilenameComplete = path + csvFilename;
                System.out.println("file storing: " + csvFilenameComplete);

                // here are the data
                String[] header = {"Make", "Model", "Description", "Price"};
                String[] record1 = {"Dell", "P3421W", "Dell 34, Curved, USB-C Monitor", "2499.00"};
                String[] record2 = {"Dell", "", "Alienware 38 Curved \"Gaming Monitor\"", "6699.00"};
                String[] record3 = {"Samsung", "", "49\" Dual QHD, QLED, HDR1000", "6199.00"};
                String[] record4 = {"Samsung", "", "Promotion! Special Price\n49\" Dual QHD, QLED, HDR1000", "4999.00"};

                List<String[]> list = new ArrayList<>();
                list.add(header);
                list.add(record1);
                list.add(record2);
                list.add(record3);
                list.add(record4);

                CsvWriterSimple writer = new CsvWriterSimple();
                try {
                    writer.writeToCsvFile(list, new File(csvFilenameComplete));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = getFilesDir().getAbsolutePath();
                String csvFilenameComplete = path + csvFilename;
                System.out.println("file reading: " + csvFilenameComplete);
                // check if file exists before reading
                File csvReadingFile = new File(csvFilenameComplete);
                boolean csvReadingFileExists = csvReadingFile.exists();
                System.out.println("The file is existing: " + csvReadingFileExists);
                String completeContent = "";
                if (csvReadingFileExists) {
                    try {
                        CsvParserSimple obj = new CsvParserSimple();
                        List<String[]> result = null;
                        result = obj.readFile(csvReadingFile, 1);
                        int listIndex = 0;
                        for (String[] arrays : result) {
                            System.out.println("\nString[" + listIndex++ + "] : " + Arrays.toString(arrays));
                            completeContent = completeContent + "[nr " + listIndex + "] : " + Arrays.toString(arrays) + "\n";
                            completeContent = completeContent + "-----------------\n";
                            int index = 0;
                            for (String array : arrays) {
                                System.out.println(index++ + " : " + array);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                etContent.setText(completeContent);
            }
        });

    }
}