
package ar.com.kimboo.r2d2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ApplicationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_application, menu);
        return true;
    }

}
