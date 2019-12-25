package ml.huytools.lib;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ml.huytools.lib.App;

public class Resource {

    public static String readRawTextFile(int resId)
    {
        InputStream inputStream = App.getInstance().getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    public static int getResourceID(String pathResource){
        Resources res = App.getInstance().getResources();
        String package_name = App.getInstance().getPackageName();
        Pattern pattern = Pattern.compile("^R\\.([\\w]+)\\.([\\w]+)$");
        Matcher matcher = pattern.matcher(pathResource);

        if (matcher.find()) {
            return res.getIdentifier(matcher.group(2), matcher.group(1), package_name);
        }

        return 0;
    }
}
