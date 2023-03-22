package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity2 extends AppCompatActivity {

    private Menu menuact;
    private ArrayList<newsobj> nlist =new ArrayList<>();
    RecyclerView newsview;
    ActionBar actionBar;
    newsrecycle newsadapter;
    MenuItem starItem;
    boolean isfav;
    public String ticker;
    private ArrayList<PortfolioObject> portArray = new ArrayList<>();
    private ArrayList<favoriteobj> favArray = new ArrayList<>();
    public static final  String Extra_Text = "com.example.myapplication.Extra_Text";
    gdbutton but = new gdbutton(Activity2.this);
    Button bt;
    Dialog dialog;
    Dialog dialog2;
    Dialog dialog3;
    public ArrayList<Double> balsb = new ArrayList<>();
    public companydata cd;
//    public PriceData pd;
    public PriceData pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ShowPortfolio();
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#D3D3D3"));
        actionBar.setBackgroundDrawable(colorDrawable);

        Intent intent = getIntent();
        String input = intent.getStringExtra(MainActivity.Extra_Text);
        ticker = input;
        setTitle(input);

        DsiplayStockInfo(input);

        ImageView firstimg = findViewById(R.id.firstimg);
        Picasso.get().load("https://s.yimg.com/uu/api/res/1.2/fwWeQik9uk4XUo7EFZkr1Q--~B/aD01NjU7dz0xMDAwO2FwcGlkPXl0YWNoeW9u/https://media.zenfs.com/en/ibd.com/55e7bb43101ea6303761180bc44efebc").into(firstimg);
        firstimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.show();
            }
        });


        //Toast.makeText(Activity2.this, "In activity 2"+ input, Toast.LENGTH_SHORT).show();
        bt = (Button) findViewById(R.id.trade);
        bt.setBackgroundColor(Color.WHITE);

        dialog = new Dialog(Activity2.this);
        dialog.setContentView(R.layout.dialog_lay);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogcurve));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog2 = new Dialog(Activity2.this);
        dialog2.setContentView(R.layout.congrats_layout);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.cong_curve));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog3 = new Dialog(Activity2.this);
        dialog3.setContentView(R.layout.news_dialog);
        dialog3.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogcurve));
        dialog3.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
        ImageView chrome = dialog3.findViewById(R.id.chrome);
        ImageView twitter = dialog3.findViewById(R.id.twitter);
        ImageView facebook = dialog3.findViewById(R.id.facebook);

        chrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://finnhub.io/api/news?id=9f4939ca6d4a7162ce9869cdf3f4dfe91ed3932bf428bf2d07c106777b742821"));
                startActivity(browserIntent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://twitter.com/i/flow/login"));
                startActivity(browserIntent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.facebook.com/"));
                startActivity(browserIntent);
            }
        });



        Button buy = dialog.findViewById(R.id.buy);
        Button sell = dialog.findViewById(R.id.sell);
        TextView title = dialog.findViewById(R.id.title);
        TextView calculation = dialog.findViewById(R.id.calculation);
        TextView balance = dialog.findViewById(R.id.balance);
        EditText sharesnumber = dialog.findViewById(R.id.sharesnumber);
        TextView finalcongo = dialog2.findViewById(R.id.finalcongo);




        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText share = (EditText) dialog.findViewById(R.id.sharesnumber);
                //Toast.makeText(Activity2.this, share.getText(),Toast.LENGTH_SHORT).show();
                String value= share.getText().toString();
                if(value.equals("a") || value.equals("''") || value.equals(".")){
                    Toast.makeText(Activity2.this, "Please entre a valid amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    int finalValue=Integer.parseInt(value);
                    int current_shares = GetCurrentShares(ticker);

                    if (finalValue <= 0) {
                        Toast.makeText(Activity2.this, "Cannot buy non-positive shares", Toast.LENGTH_SHORT).show();
                    }
                    else if(!getCurrentBalance(finalValue)){
                        Toast.makeText(Activity2.this, "Not enough money to buy", Toast.LENGTH_SHORT).show();

                    }
                    else if (current_shares == 0) {
//                        storeFavorite();
                        //Toast.makeText(Activity2.this, "In fill", Toast.LENGTH_SHORT).show();
                        filldata(ticker, finalValue);
                        dialog.dismiss();
                        finalcongo.setText("You have successfully bought "+ String.valueOf(finalValue)+" shares of "+ticker);
                        dialog2.show();
                        Button done = dialog2.findViewById(R.id.done);
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fillportfolioscreen();
                                dialog2.dismiss();
                            }
                        });
                    }

                    else {
                        //Toast.makeText(Activity2.this, "In update", Toast.LENGTH_SHORT).show();
//                        storeFavorite();
                        updateData(ticker, finalValue);
                        dialog.dismiss();
                        finalcongo.setText("You have successfully bought "+String.valueOf(finalValue)+" shares of "+ticker);
                        dialog2.show();
                        TextView finalcongo = (TextView) dialog2.findViewById(R.id.finalcongo);
                        Button done = dialog2.findViewById(R.id.done);
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fillportfolioscreen();

                                dialog2.dismiss();
                            }
                        });




                    }
                }

            }
        });


        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText share = (EditText) dialog.findViewById(R.id.sharesnumber);
                //Toast.makeText(Activity2.this, share.getText(),Toast.LENGTH_SHORT).show();
                String value= share.getText().toString();
                if(value.equals("a") || value.equals("''") || value.equals(".")){
                    Toast.makeText(Activity2.this, "Please entre a valid amount", Toast.LENGTH_SHORT).show();
                }
                else{
                    int finalValue=Integer.parseInt(value);
                    int current_shares = GetCurrentShares(ticker);
                    TextView tx = (TextView) findViewById(R.id.calculation);
                    if (finalValue <= 0) {
                        Toast.makeText(Activity2.this, "Cannot sell non-positive shares", Toast.LENGTH_SHORT).show();
                    } else if (finalValue > current_shares) {
                        Toast.makeText(Activity2.this, "‘Not enough shares to sell", Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(Activity2.this, "In update", Toast.LENGTH_SHORT).show();

                        updateSellData(ticker, finalValue);
                        dialog.dismiss();
                        finalcongo.setText("You have successfully sold "+String.valueOf(finalValue)+" shares of "+ticker);

                        dialog2.show();
                        TextView finalcongo = (TextView) dialog2.findViewById(R.id.finalcongo);
                        Button done = dialog2.findViewById(R.id.done);
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fillportfolioscreen();
                                dialog2.dismiss();
                            }
                        });

//                        removeFavorite();
                    }
                }

            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextWatcher inputTextWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){
                    }
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(s.length()>0 && s.charAt(0) != '-' && s.charAt(0) != 'a' && s.charAt(0) != '.'){
                            int number = Integer.parseInt(s.toString());
                        calculation.setText(String.valueOf(number)+"*$"+String.format("%.2f",pd.getC())+"/share = "+String.format("%.2f",number*pd.getC()));
//                            calculation.setText(String.format("%2f",number*pd.getC()));
                        }


                    }
                };

                sharesnumber.addTextChangedListener(inputTextWatcher);
                title.setText(cd.getTicker()+" "+ cd.getName()+" shares");
                CHECKBALANCE();
                balance.setText("$"+String.format("%.2f",balsb.get(1))+" to buy "+ ticker);
                dialog.show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorite, menu);
        this.menuact = menu;
        starItem = this.menuact.findItem(R.id.star);
        setfav();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.star ) {
            checkfav();
        }else{
            onBackPressed();
        }
        return true;
    }

    public void checkfav(){
        SharedPreferences sharedPreferences = getSharedPreferences("Favorite", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Favorite", "[]");
        //System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<favoriteobj>>(){}.getType();
        favArray = gson.fromJson(json, ptype);
        int flag = 0;
        for(int i=0; i< favArray.size();i++){
            if(favArray.get(i).getTicker().equals(ticker)){
                flag =1;
            }
        }

        if(flag == 1){
            starItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_outline_24));
            Toast.makeText(Activity2.this,ticker+" removed from favorites",Toast.LENGTH_SHORT).show();
            removeFavorite();
        }
        else{
            starItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24));
            Toast.makeText(Activity2.this,ticker+" added to favorites",Toast.LENGTH_SHORT).show();
            storeFavorite();

        }

    }


    public void setfav(){
        SharedPreferences sharedPreferences = getSharedPreferences("Favorite", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Favorite", "[]");
        //System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<favoriteobj>>(){}.getType();
        favArray = gson.fromJson(json, ptype);
        int flag = 0;
        for(int i=0; i< favArray.size();i++){
            if(favArray.get(i).getTicker().equals(ticker)){
                flag =1;
            }
        }

        if(flag == 1){
            starItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24));
            //Toast.makeText(Activity2.this,ticker+" Already in favorites",Toast.LENGTH_SHORT).show();
        }
        else{
            starItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_outline_24));
           // Toast.makeText(Activity2.this,ticker+" Not in favorites",Toast.LENGTH_SHORT).show();
        }

    }

    private void DsiplayStockInfo(String input) {
        TextView pricetx = (TextView) findViewById(R.id.price);
        TextView pricechangetx = (TextView) findViewById(R.id.pricechange);
        TextView companysymbol = (TextView) findViewById(R.id.companysymbol);
        TextView companyname = (TextView) findViewById(R.id.companyname);
        ImageView trendup = (ImageView) findViewById(R.id.a2tu);
        ImageView trenddown = (ImageView) findViewById(R.id.a2td);


        but.symbol(input, new gdbutton.VolleyResponseListener() {
            @Override
            public void onError(String message) {
               // Toast.makeText(Activity2.this,"Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(companydata data) {
               // Toast.makeText(Activity2.this,"In stockinfo: "+data.toString(),Toast.LENGTH_SHORT).show();
                cd = data;
                companysymbol.setText(cd.getTicker());
                companyname.setText(cd.getName());
                System.out.println("After cd"+ cd.toString());
                ImageView img = findViewById(R.id.logo);
                String url = cd.getLogo();

                Picasso.get().load(url).into(img);
                fillabovescreen();

            }
        });


        but.latestprice(input, new gdbutton.LatestPriceListener(){
            @Override
            public void onError(String message) {
               // Toast.makeText(Activity2.this,"Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(PriceData data) {
                System.out.println("In stock"+data.toString());
               // Toast.makeText(Activity2.this,"In latestprice: "+data.toString(),Toast.LENGTH_SHORT).show();
                pd = data;
                pricetx.setText("$"+String.valueOf(pd.getC()));
                Double change = pd.getD();
                Double percentage = pd.getDp();
                String changePriceStr;
                String a;
                String b;
                int textColor;

                Double condition = change;
                change = Math.abs(change);
                a = String.format("$%.2f", change);
                percentage =  Math.abs(percentage);
                b =  String.format(" (%.2f", percentage);
                String c = "%)";
                changePriceStr = a+b+c;

                if (condition > 0) {
                    textColor = Color.rgb(49, 156, 94); // green
                    trendup.setVisibility(View.VISIBLE);
                }

                else if(condition<0)
                {
                    textColor = Color.rgb(155, 64, 73); //red
                    trenddown.setVisibility(View.VISIBLE);
                }
                else{
                    textColor = Color.rgb(0, 0, 0); // green
                    trendup.setVisibility(View.INVISIBLE);
                    trenddown.setVisibility(View.INVISIBLE);
                }

                pricechangetx.setText(changePriceStr);
                pricechangetx.setTextColor(textColor);

                pricetx.setTextColor(textColor);



                fillstatsscreen();

                fillportfolioscreen();

            }
        });



        TabLayout tb = (TabLayout) findViewById(R.id.tabs);
        TabItem t1 = (TabItem) findViewById(R.id.t1);
        TabItem t2 = (TabItem) findViewById(R.id.t2);
        ViewPager2 vp = (ViewPager2) findViewById(R.id.vp2);

        PagerAdapter pa = new PagerAdapter(getSupportFragmentManager(),getLifecycle(),tb.getTabCount(),ticker);
        vp.setAdapter(pa);
        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        WebView swebview = findViewById(R.id.sentimentview);
        swebview.getSettings().setJavaScriptEnabled(true);
        swebview.getSettings().setAllowFileAccess(true);
        swebview.setWebChromeClient(new WebChromeClient());
        swebview.loadUrl("file:///android_asset/table.html");
        swebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:setTicker('" + input + "')");
            }
        });

        WebView rwebview = findViewById(R.id.recommendationwebview);
        rwebview.getSettings().setJavaScriptEnabled(true);
        rwebview.getSettings().setAllowFileAccess(true);
        rwebview.setWebChromeClient(new WebChromeClient());
        rwebview.loadUrl("file:///android_asset/trending.html");
        rwebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:setTicker('" + input + "')");
            }
        });

        WebView ewebview = findViewById(R.id.earningwebview);
        ewebview.getSettings().setJavaScriptEnabled(true);
        ewebview.getSettings().setAllowFileAccess(true);
        ewebview.setWebChromeClient(new WebChromeClient());
        ewebview.loadUrl("file:///android_asset/Earnings.html");
        ewebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:setTicker('" + input + "')");
            }
        });


        TextView tx6 = (TextView) findViewById(R.id.textView6);
        fillnews();
        newsview = findViewById(R.id.newsrecycle);
        newsadapter = new newsrecycle(nlist);
        newsview.setAdapter(newsadapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        newsview.addItemDecoration(dividerItemDecoration);
        tx6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                openstockinfo2("DELL");
            }
        });

    }


    public  void  openstockinfo2(String inp){
        Intent intent = new Intent(this, Activity2.class );
        System.out.println("Company");
        intent.putExtra(Extra_Text,inp);
        startActivity(intent);
    }

    public boolean getCurrentBalance(int finalvalue){

        SharedPreferences sharedPreferences = getSharedPreferences("Balance", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json3 = sharedPreferences.getString("Balance", "[]");
        Double[] sb;
        Gson gson = new Gson();
        Type sbtype = new TypeToken<Double[]>(){}.getType();
        sb = gson.fromJson(json3, sbtype);
        boolean state = false;
//        if(sb[1]>finalvalue*pd.getC()){
//            state = true;
//        }
        if(sb[1]>finalvalue*pd.getC()){
            state = true;
        }
        return state;
    }
    public int GetCurrentShares(String inp){
        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");
        System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        portArray = gson.fromJson(json, ptype);
        int cnt = 0;
        for(int i= 0;i < portArray.size();i++){
           // Toast.makeText(Activity2.this, "In update"+portArray.get(i).getTicker(), Toast.LENGTH_SHORT).show();

            if(portArray.get(i).getTicker().equals(inp)){

                cnt = portArray.get(i).getShares();
            }
        }
        return cnt;
    }

    public void updateData(String inp, int finalvalue){
        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");
        System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        portArray = gson.fromJson(json, ptype);
        int cnt = 0;
        Double buyvalue =0.0;
        for(int i= 0;i < portArray.size();i++){

            if(portArray.get(i).getTicker().equals(inp)){
                portArray.get(i).setShares(portArray.get(i).getShares() + finalvalue);
                buyvalue = pd.getC()*finalvalue;
                portArray.get(i).setPrice(portArray.get(i).getPrice()+pd.getC()*finalvalue);
                cnt = i;
            }
        }

        String json2 = gson.toJson(portArray);
        editor.putString("Portfolio",json2 );
        editor.commit();
        SharedPreferences sharedPreferences2 = getSharedPreferences("Balance", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        json = sharedPreferences2.getString("Balance", "[]");
        Double[] sb;
        //Toast.makeText(Activity2.this,"I am in sp after sb" +json,Toast.LENGTH_SHORT).show();
        Type sbtype = new TypeToken<Double[]>(){}.getType();
        sb = gson.fromJson(json, sbtype);
        sb[1] = sb[1] - buyvalue;
        sb[0] = sb[1] + buyvalue;
        balsb.set(0,sb[0]);
        balsb.set(1,sb[1]);
        System.out.println("balsb"+balsb);

        json = gson.toJson(sb);
        editor2.putString("Balance",json );
        editor2.commit();
    }


    public void updateSellData(String inp, int finalvalue){
        //.makeText(Activity2.this,"subtract",Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");

        System.out.println("In show2"+json);


        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        portArray = gson.fromJson(json, ptype);
        int cnt = 0;
        int flag = 0;
        //Toast.makeText(Activity2.this,"subtract"+(portArray.get(0).getShares() - finalvalue),Toast.LENGTH_SHORT).show();
        Double soldvalue = 0.0;
        for(int i= 0;i < portArray.size();i++){

            if(portArray.get(i).getTicker().equals(inp)){
                soldvalue = finalvalue*pd.getC();
                portArray.get(i).setPrice(portArray.get(i).getPrice()-soldvalue);
                portArray.get(i).setShares(portArray.get(i).getShares() - finalvalue);
                if(portArray.get(i).getShares() == 0){
                    //Toast.makeText(Activity2.this,"subtract"+(portArray.get(i).getShares() - finalvalue),Toast.LENGTH_SHORT).show();
                    flag = 1;
                    cnt = i;
                }
            }
        }

        if(flag == 1) {
            portArray.remove(cnt);
        }
        String json2 = gson.toJson(portArray);
        editor.putString("Portfolio",json2 );
        editor.commit();

        SharedPreferences sharedPreferences2 = getSharedPreferences("Balance", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        json = sharedPreferences2.getString("Balance", "[]");
        Double[] sb;
        //Toast.makeText(Activity2.this,"I am in selldata after sb" +json,Toast.LENGTH_SHORT).show();
        Type sbtype = new TypeToken<Double[]>(){}.getType();
        sb = gson.fromJson(json, sbtype);
        sb[1] = sb[1] + soldvalue;
        sb[0] = sb[0] - soldvalue;
        balsb.set(0,sb[0]);
        balsb.set(1,sb[1]);

        System.out.println("balsb"+balsb);
        json = gson.toJson(sb);
        editor2.putString("Balance",json );
        editor2.commit();



    }



    public void filldata(String input, int finalvalue) {
        but.latestprice(input, new gdbutton.LatestPriceListener(){
            @Override
            public void onError(String message) {
               // Toast.makeText(Activity2.this,"Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(PriceData data) {
                System.out.println("In stock"+data.toString());
                //Toast.makeText(Activity2.this,"In latestprice: "+data.toString(),Toast.LENGTH_SHORT).show();
                pd = data;
                PortfolioObject pobj = new PortfolioObject(pd.getTicker(),pd.getC()*finalvalue,finalvalue,0.0,0.0);
                storePortfolio(pobj);
            }
        });

    }

    public void storePortfolio(PortfolioObject pobj){

        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");
        System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        portArray = gson.fromJson(json, ptype);
        portArray.add(pobj);
        System.out.println("In store");
        String json2 = gson.toJson(portArray);
        editor.putString("Portfolio",json2 );
        editor.commit();
        Gson gson2 = new Gson();
        SharedPreferences sharedPreferences2 = getSharedPreferences("Balance", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        String json3 = sharedPreferences2.getString("Balance", "[]");
        Double[] sb;
        Type sbtype = new TypeToken<Double[]>(){}.getType();
        sb = gson2.fromJson(json3, sbtype);
        sb[1] = sb[1] - pobj.getPrice();
        sb[0] = sb[1] + pobj.getPrice();
        balsb.set(0,sb[0]);
        balsb.set(1,sb[1]);
        System.out.println("balsb"+balsb);
        json3 = gson2.toJson(sb);
       // Toast.makeText(Activity2.this,"I am in sp after sb" +json3,Toast.LENGTH_SHORT).show();
        editor2.putString("Balance",json3 );
        editor2.commit();

    }

    public void storeFavorite(){

        SharedPreferences sharedPreferences = getSharedPreferences("Favorite", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Favorite", "[]");
        //System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<favoriteobj>>(){}.getType();
        favArray = gson.fromJson(json, ptype);
        favoriteobj fobj = new favoriteobj(cd.getTicker(), pd.getC(),cd.getName(), pd.getD(),pd.getDp());
        favArray.add(fobj);
        System.out.println("In store");
        String json2 = gson.toJson(favArray);
       // Toast.makeText(Activity2.this,"In Activity 2 store fav"+json2,Toast.LENGTH_SHORT).show();
        editor.putString("Favorite",json2 );
        editor.commit();

    }

    public void removeFavorite(){
        SharedPreferences sharedPreferences = getSharedPreferences("Favorite", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Favorite", "[]");
//        System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<favoriteobj>>(){}.getType();
        favArray = gson.fromJson(json, ptype);
        int cnt = 0;
        for(int i= 0;i < favArray.size();i++){

            if(favArray.get(i).getTicker().equals(ticker)){
                cnt = i;
            }
        }

        favArray.remove(cnt);
        json = gson.toJson(favArray);
        editor.putString("Favorite",json );
        editor.commit();

    }

    public void fillportfolioscreen(){
        TextView pt1 = (TextView) findViewById(R.id.p1);
        TextView pt2 = (TextView) findViewById(R.id.p2);
        TextView pt3 = (TextView) findViewById(R.id.p3);
        TextView pt4 = (TextView) findViewById(R.id.p4);
        TextView pt5 = (TextView) findViewById(R.id.p5);
        pt1.setText(String.valueOf(0));
        pt2.setText("$"+String.format("%.2f",0.0));
        pt3.setText("$"+String.format("%.2f",0.0));
        pt4.setText("$"+String.format("%.2f",0.0));
        pt5.setText("$"+String.format("%.2f",0.0));

        SharedPreferences sharedPreferences = getSharedPreferences("Portfolio", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Portfolio", "[]");
        System.out.println("In show2"+json);
        Type ptype = new TypeToken<ArrayList<PortfolioObject>>(){}.getType();
        portArray = gson.fromJson(json, ptype);

        for(int i= 0;i < portArray.size();i++){
            if(portArray.get(i).getTicker().equals(ticker)){
                pt1.setText(String.valueOf(portArray.get(i).getShares()));
                pt2.setText("$"+String.format("%.2f",portArray.get(i).getPrice()/portArray.get(i).getShares()));
                pt3.setText("$"+String.format("%.2f",portArray.get(i).getPrice()));
                pt4.setText("$"+String.format("%.2f",portArray.get(i).getPrice()/portArray.get(i).getShares() - pd.getC()));
                pt5.setText("$"+String.format("%.2f",portArray.get(i).getShares()*pd.getC()));
            }
        }


    }

    public void fillstatsscreen(){
        TextView s1 = (TextView) findViewById(R.id.s1);
        TextView s2 = (TextView) findViewById(R.id.s2);
        TextView s3 = (TextView) findViewById(R.id.s3);
        TextView s4 = (TextView) findViewById(R.id.s4);
        s1.setText("$"+String.format("%.2f",pd.getO()));
        s2.setText("$"+String.format("%.2f",pd.getH()));
        s3.setText("$"+String.format("%.2f",pd.getL()));
        s4.setText("$"+String.format("%.2f",pd.getPc()));
    }

    public void fillabovescreen(){
        TextView a1 = (TextView) findViewById(R.id.a1);
        TextView a2 = (TextView) findViewById(R.id.a2);
        TextView a3 = (TextView) findViewById(R.id.a3);
        a1.setText(cd.getIpo());
        a2.setText(cd.getFinnhubIndustry());
        a3.setText(cd.getWeburl());

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(cd.getWeburl()));
                startActivity(browserIntent);
            }
        });

    }



    public void CHECKBALANCE(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("Balance", Activity2.this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        String json = sharedPreferences2.getString("Balance", "[]");
        Double[] sb;
        Gson gson = new Gson();
        //Toast.makeText(Activity2.this,"I am in selldata after sb" +json,Toast.LENGTH_SHORT).show();
        Type sbtype = new TypeToken<Double[]>(){}.getType();
        sb = gson.fromJson(json, sbtype);
        balsb.add(0,sb[0]);
        balsb.add(1,sb[1]);

   }

   public void fillnews(){

       nlist.add(new newsobj(
               "4 hours ago",
               "Market check: Stock sell-off accelerates as traders weigh Fed policy",
               "https://s.yimg.com/ny/api/res/1.2/a_FWD7lWpsrohD6VkGihmg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD02NzU-/https://s.yimg.com/hd/cp-video-transcode/production/013c86d9-24b7-399d-bca4-9594cde23f8a/2022-05-05/16-14-45/5814a8d4-ec9b-53d1-8f1e-4fcd99410e8f/stream_1280x720x0_v2_3_0.jpg",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "4 hours ago",
               "Apple, Google and Microsoft take major step to get rid of passwords",
               "https://static.independent.co.uk/2022/03/30/17/iStock-1387373490.jpg?quality=75&width=1200&auto=webp",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "6 hours ago",
               "Stock market: Dow falls 900+ points, Nikola shares rise",
               "https://s.yimg.com/ny/api/res/1.2/SFC2V0Z3LkXfsKqrjsS.fQ--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD02NzU-/https://s.yimg.com/hd/cp-video-transcode/production/7abcfed4-fdc3-32b7-b2e2-4fb6ca1b77bc/2022-05-05/15-21-36/b7d4117d-0536-5fad-93ca-262b54a4853a/stream_1280x720x0_v2_3_0.jpg",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "6 hours ago",
               "Apple, Google and Microsoft agree to support ‘passwordless’ sign-ins",
               "https://static.independent.co.uk/2022/05/05/16/42b83c3bc62a07833669f50cbdd9f74bY29udGVudHNlYXJjaGFwaSwxNjUxODUwMTIz-2.63888354.jpg?quality=75&width=1200&auto=webp",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "8 hours ago",
               "Apple has spent decades building its walled garden. It may be starting to crack",
               "https://s.yimg.com/ny/api/res/1.2/S5EPse.30OGLMBL8p4M7FA--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD02MDA-/https://s.yimg.com/uu/api/res/1.2/RTJnl5uSVpGoimzljFoSGw--~B/aD02NDA7dz0xMjgwO2FwcGlkPXl0YWNoeW9u/https://media.zenfs.com/en/marketwatch.com/3eceedae62a598145c69798f7647c1ae",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "8 hours ago",
               "Supply-Chain Woes Hitting Apple for Billions",
               "https://s.yimg.com/ny/api/res/1.2/dYfOwJTKVfsLrx0tiwZ_Hw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD04MDA-/https://s.yimg.com/uu/api/res/1.2/F6FeIebiU16RJpTKMcBaKQ--~B/aD05MzM7dz0xNDAwO2FwcGlkPXl0YWNoeW9u/https://media.zenfs.com/en/motleyfool.com/c5960d7e6dc3454795e18b977b6b8847",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "8 hours ago",
               "Apple, Google and Microsoft expand use of thumb and face ID to replace passwords",
               "https://s.yimg.com/ny/api/res/1.2/7qw0ZiadjaiQjAeQibKQRw--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD03NTA-/https://s.yimg.com/uu/api/res/1.2/Lu5xL6oPHbEvYg6pynuIyw--~B/aD0xNTYzO3c9MjUwMDthcHBpZD15dGFjaHlvbg--/https://media.zenfs.com/en/the_telegraph_818/45fdc0032bb21de8c2c84a908aef27e9",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "9 hours ago",
               "Brad Gerstner is Talking About These 10 Stocks",
               "https://s.yimg.com/uu/api/res/1.2/7pY0Oi219JgD_tbaQfIrkw--~B/aD02MDA7dz00NjQ7YXBwaWQ9eXRhY2h5b24-/https://media.zenfs.com/en/insidermonkey.com/3b4809278c90636e9fd5c5a032a8ddc2",
               "Yahoo"
               ));
       nlist.add(new newsobj(
               "9 hours ago",
               "Dow Jones Futures Fall After Fed's Powell Fuels Big Market Rally; Apple, Exxon Flash Buy Signals",
               "https://s.yimg.com/uu/api/res/1.2/1ZxbIIte175FM72CDAp1Mg--~B/aD01NjM7dz0xMDAwO2FwcGlkPXl0YWNoeW9u/https://media.zenfs.com/en/ibd.com/072cc1cb987ee93017a2c4d63e4be893",
               "Yahoo"
               ));
        System.out.println("hi there"+nlist.get(0).getImg());
   }


}