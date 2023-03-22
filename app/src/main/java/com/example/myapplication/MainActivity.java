package com.example.myapplication;
//new
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    RecyclerView recyclerView;
    recycleAdapter radapter;
    List<String> l;
    ArrayList<PortfolioObject> portArray = new ArrayList<>();
    ArrayList<favoriteobj> favArray = new ArrayList<>();
    RecyclerView precyclerView;
    PortrecycleAdapter padapter;
    ArrayAdapter<String> adapter;
    public ArrayList<String> autodisplay = new ArrayList<>();
    public gdbutton gdobj = new gdbutton(MainActivity.this);
    PriceData data;
    PortfolioObject pobj;
    ActionBar actionBar;


    public int cnt = 0;
    public static final  String Extra_Text = "com.example.myapplication.Extra_Text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Stocks");
        setContentView(R.layout.activity_main);
        //clearData();
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#D3D3D3"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#000000\">" + getString(R.string.Stocks) + "</font>")));
        showdate();
        //setBalance();
        //updateBalance();
        showfooter();
        //storePortfolio();
        ShowPortfolio();
        ShowFavorite();
        //handleFav();

        //every15();

        ItemTouchHelper pitemTouchHelper = new ItemTouchHelper(simpleCallback2);
        pitemTouchHelper.attachToRecyclerView(precyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

//        Button bt = (Button) findViewById(R.id.button);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent activity2Intent = new Intent(getApplicationContext(), Activity2.class);
////                startActivity(activity2Intent);
//                openstockinfo("TSLA");
//            }
//        });

    }

    public void every15(){
        Runnable helloRunnable = new Runnable() {
            public void run() {
                //Toast.makeText(MainActivity.this,"Runnable"+ cnt++,Toast.LENGTH_SHORT).show();
                System.out.println("In every15"+cnt++);
                UpdatePortfolioagain();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 15, TimeUnit.SECONDS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
               // Toast.makeText(MainActivity.this, "Clicked",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
              // Toast.makeText(MainActivity.this, "Colapsed",Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        menu.findItem(R.id.menu_search).setOnActionExpandListener(onActionExpandListener);
        MenuItem searchMenu = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setQueryHint("Search...");
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, autodisplay);

        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);
        searchAutoComplete.setTextColor(Color.parseColor("#000000"));


        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {

                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                String[] splited = queryString.split("\\s+");
                searchAutoComplete.setText("" + splited[0]);
                //.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
                openstockinfo(splited[0]);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getAutoCompleteData(newText);
                return false;
            }
        });

        return true;
    }

    private void getAutoCompleteData(String newText) {
        gdobj.autocompletecall(newText, new gdbutton.AutoCompleteListener() {
            @Override
            public void onError(String message) {
               // Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<AutocompleteObj> resau) {
                //Toast.makeText(MainActivity.this,"IN MAIN: "+resau.size(),Toast.LENGTH_SHORT).show();
                for(int i=0; i < resau.size(); i++){
                    String sym = resau.get(i).getSymbol();
                    String desc = resau.get(i).getDescription();
                    autodisplay.add(sym+" | "+desc);
                }
                adapter.clear();
                adapter.addAll(autodisplay);
                adapter.notifyDataSetChanged();
            }

        });
    }

    private void openstockinfo(String s) {
        Intent intent = new Intent(this, Activity2.class );
        String inp = s;
        System.out.println("Company");
        intent.putExtra(Extra_Text,inp);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowPortfolio();
       updateBalance();
       ShowFavorite();
       //every15();

    }

//    public void handleFav(){
//        l = new ArrayList<>();
//        l.add("TSLA");
//        l.add("AAPL");
//        l.add("sas");
//
//        recyclerView = findViewById(R.id.rv);
//        radapter = new recycleAdapter(l);
//        recyclerView.setAdapter(radapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
//
//    }

    public void updatePortfolioRecycle(ArrayList<PortfolioObject> portArray2){
       // Toast.makeText(MainActivity.this,"In Update"+portArray2.size(),Toast.LENGTH_SHORT).show();
        precyclerView = findViewById(R.id.rv2);
        padapter = new PortrecycleAdapter(portArray2);
        precyclerView.setAdapter(padapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        precyclerView.addItemDecoration(dividerItemDecoration);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(favArray, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int pos = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT){
                    radapter.notifyItemRemoved(pos);
                    removeFavorite(favArray.get(pos).getTicker());
                }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(portArray, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

//    public void storePortfolio(){
//        System.out.println("In store");
//        PortfolioObject pobj = new PortfolioObject("TSLA",232.0,1,23.0,23.0);
//        portArray.add(pobj);
//        portArray.add(pobj);
//        portArray.add(pobj);
//        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", MainActivity.this.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(portArray);
//        editor.putString("Portfolio",json );
//        editor.commit();
//        System.out.println("In store2"+json);
//
//    }
    public void ShowPortfolio(){
        //System.out.println("In show1");

        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");

        //System.out.println("In show2"+json);


        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        ArrayList<PortfolioObject> portArray2 = gson.fromJson(json, ptype);
        portArray = portArray2;

        for(int i =0; i <portArray2.size();i++){
            //System.out.println("In Show Portfolio:"+i+":"+portArray2.get(i).toString());
            //Toast.makeText(MainActivity.this,"In Show Portfolio"+ portArray2.get(i).toString(),Toast.LENGTH_SHORT).show();
        }

        updatePortfolioRecycle(portArray2);

    }

    public void showdate(){
        TextView datetx = (TextView) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMM yyyy");
        date = dateFormat.format(calendar.getTime());
        datetx.setText(date);
    }

    public void showfooter(){
        TextView footer = (TextView)findViewById(R.id.footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://finnhub.io/"));
                startActivity(browserIntent);
            }
        });
    }

    public void clearData(){
        SharedPreferences port = MainActivity.this.getSharedPreferences("Portfolio", MainActivity.this.MODE_PRIVATE);
        port.edit().clear().commit();
        SharedPreferences bal = MainActivity.this.getSharedPreferences("Balance", MainActivity.this.MODE_PRIVATE);
        bal.edit().clear().commit();
        SharedPreferences fav = MainActivity.this.getSharedPreferences("Favorite", MainActivity.this.MODE_PRIVATE);
        fav.edit().clear().commit();
    }


    public void setBalance(){
        System.out.println("set bal");
        SharedPreferences sharedPreferences = getSharedPreferences("Balance", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Double[] sb = new Double[2];
        sb[0] = 25000.0;
        sb[1] = 25000.0;
        TextView nwp = (TextView) findViewById(R.id.pricenetworth);
        nwp.setText("$"+String.format("%.2f", sb[0]));
        TextView balp = (TextView) findViewById(R.id.cashbalance);
        balp.setText("$"+String.format("%.2f", sb[1]));
        Gson gson = new Gson();
        String json = gson.toJson(sb);
        editor.putString("Balance",json );
        editor.commit();
    }

    public void updateBalance(){
        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");

        ArrayList<PortfolioObject> upbarray = new ArrayList<>();
        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        upbarray = gson.fromJson(json, ptype);
        double totalValue = 0.0;

        for(int i= 0;i < upbarray.size();i++){
                totalValue += upbarray.get(i).getPrice();
            }

       // Toast.makeText(MainActivity.this,"add"+totalValue,Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences2 = getSharedPreferences("Balance", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        String json2 = sharedPreferences2.getString("Balance", "[]");
        Double[] sb;
        Type sbtype = new TypeToken<Double[]>(){}.getType();
        sb = gson.fromJson(json2, sbtype);
        //ast.makeText(MainActivity.this,"add"+totalValue+" "+sb[0]+" "+sb[1],Toast.LENGTH_SHORT).show();

        sb[0] = sb[1] + totalValue;
        TextView nwp = (TextView) findViewById(R.id.pricenetworth);
        nwp.setText("$"+String.format("%.2f", sb[0]));
        TextView balp = (TextView) findViewById(R.id.cashbalance);
        balp.setText("$"+String.format("%.2f", sb[1]));
        json2 = gson.toJson(sb);
        editor2.putString("Balance",json2);
        editor2.commit();

    }


    public void removeFavorite(String tickervalue){
        SharedPreferences sharedPreferences = getSharedPreferences("Favorite", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Favorite", "[]");
//        System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<favoriteobj>>(){}.getType();
        favArray = gson.fromJson(json, ptype);
        int cnt = 0;
        for(int i= 0;i < favArray.size();i++){

            if(favArray.get(i).getTicker().equals(tickervalue)){
                cnt = i;
            }
        }

        favArray.remove(cnt);
        json = gson.toJson(favArray);
        editor.putString("Favorite",json );
        editor.commit();
        ShowFavorite();

    }

    public void ShowFavorite(){
//        System.out.println("In show1");
        SharedPreferences sharedPreferences = getSharedPreferences("Favorite", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Favorite", "[]");
//        System.out.println("In show2"+json);
        //Toast.makeText(MainActivity.this,"In Main store fav"+json,Toast.LENGTH_SHORT).show();

        Type ptype = new TypeToken<ArrayList<favoriteobj>>(){}.getType();
        ArrayList<favoriteobj> favArray2 = gson.fromJson(json, ptype);
        favArray = favArray2;

        for(int i =0; i <favArray2.size();i++){
            //System.out.println("In Show Portfolio:"+i+":"+portArray2.get(i).toString());
            //Toast.makeText(MainActivity.this,"In Show Portfolio"+ portArray2.get(i).toString(),Toast.LENGTH_SHORT).show();
        }

        updateFavoriteRecycle(favArray2);

    }

    public void updateFavoriteRecycle(ArrayList<favoriteobj> favArray2){
        //Toast.makeText(MainActivity.this,"In Update"+favArray2.size(),Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.rv);
        radapter = new recycleAdapter(favArray2);
        recyclerView.setAdapter(radapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    public void UpdatePortfolioagain(){

        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", MainActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");

        //System.out.println("In show2"+json);

        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        ArrayList<PortfolioObject> portArray2 = gson.fromJson(json, ptype);
        portArray = portArray2;
        for(int i =0; i <portArray2.size();i++){

            int finalI = i;
            gdobj.latestprice(portArray2.get(i).getTicker(), new gdbutton.LatestPriceListener() {
                @Override
                public void onError(String message) {
                    System.out.println("Error");
                }

                @Override
                public void onResponse(PriceData pd) {
                        System.out.println("lst function");
                        data = pd;
                        Double price = data.getC()*portArray2.get(finalI).getShares();
                        Double change = price - portArray2.get(finalI).getPrice();
                        Double dp = change/portArray2.get(finalI).getShares()*100;
                        portArray2.get(finalI).setPrice(price);
                        portArray2.get(finalI).setChangeprice(change);
                        portArray2.get(finalI).setPercentageChange(dp);
                }
            });



            String json2 = gson.toJson(portArray2);
            editor.putString("Portfolio",json2 );
            editor.commit();
            ShowPortfolio();
        }

    }

}