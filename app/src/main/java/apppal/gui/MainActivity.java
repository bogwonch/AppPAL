package apppal.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import apppal.logic.R;
import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Evaluation;
import apppal.logic.evaluation.Result;
import apppal.logic.language.Assertion;

public class MainActivity extends ActionBarActivity
{
  public final static String EXTRA_AC = "apppal.gui.AC";
  public final static String EXTRA_QUERY = "apppal.gui.QUERY";

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * Send the assertion context and query off for evaluation
   */
  public void sendQuery(View view)
  {
    final Context context = getApplicationContext();

    final EditText queryText = (EditText)findViewById(R.id.editQuery);
    final EditText acText = (EditText)findViewById(R.id.editAC);

    /*
    CharSequence text = "Query is: "+query.getText();
    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
    */

    /*
    Intent intent = new Intent(this, RunQueryActivity.class);
    intent.putExtra(EXTRA_AC, ac.getText());
    intent.putExtra(EXTRA_QUERY, query.getText());
    startActivity(intent);
    */
    try
    {
      final AC ac = new AC(acText.getText().toString());
      final Assertion q = Assertion.parse(queryText.getText().toString());

      final Evaluation e = new Evaluation(ac);
      final Result r = e.run(q);

      if (r.isProven())
      {
        view.setBackgroundColor(Color.GREEN);
        Toast.makeText(context, "Okay!", Toast.LENGTH_SHORT).show();

      }
      else
      {
        view.setBackgroundColor(Color.RED);
        Toast.makeText(context, "Nope!", Toast.LENGTH_SHORT).show();
      }

    }
    catch (Exception e)
    {
      final CharSequence text = "Error: "+e.getMessage();
      final Toast error = Toast.makeText(context, text, Toast.LENGTH_LONG);
      error.show();
    }
  }
}
