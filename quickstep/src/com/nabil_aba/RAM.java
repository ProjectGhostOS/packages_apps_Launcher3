package com.nabil_aba;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.widget.RelativeLayout;
import android.view.Gravity;
import android.database.ContentObserver;

import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.android.settingslib.Utils;

public class RAM extends RelativeLayout {
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
    String finalValue = "";
    TextView textused, textfree, ramuseger;
    int mColorAccent;
    int mColorTextPrimary;
    int mColorPrimary;
    ProgressBar kotak, bunder;
    Handler handler = new Handler();

    public RAM(Context c, AttributeSet a) {
        super(c, a);
        RAMobserver ro = new RAMobserver(handler);
        ro.NabilXSasika();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //if (!Utilities.showRamInfo(getContext())) {
        //   setVisibility(View.GONE);
        //} else {
        //   setVisibility(View.VISIBLE);
        //}

        ramuseger = findViewById(R.id.ramusage);
        textused = findViewById(R.id.nabil_usedram);
        textfree = findViewById(R.id.nabil_freeram);
        kotak = findViewById(R.id.nabil_aba_ramview_kotak);
        bunder = findViewById(R.id.nabil_aba_ramview_bunder);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                nabil_aba();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }

    public void nabil_aba() {
        activityManager.getMemoryInfo(memoryInfo);

        long total = memoryInfo.totalMem;
        long avail = memoryInfo.availMem;

        if (total / 1073741824.0 <= 3) {
            avail = memoryInfo.availMem + 233832448;
        }

        long usage = total - avail;

        String a = nabil(usage) + " " + getResources().getString(R.string.nabil_aba_used);
        String b = nabil(avail) + " " + getResources().getString(R.string.nabil_aba_free);

        mColorAccent = getResources().getColor(R.color.op_control_text_color_primary);
        mColorTextPrimary = getResources().getColor(R.color.control_text_color_primary);
	mColorPrimary = getResources().getColor(R.color.control_text_color_rprimary);

        //int anu = Settings.System.getInt(getContext().getContentResolver(), "STYLE_RAM_RECENT", 1);
        if (Utilities.styleRamInfo(getContext()) == 1) {
            setVisibility(View.VISIBLE);

            ramuseger.setTextColor(mColorTextPrimary);
            textused.setTextColor(mColorPrimary);
            textfree.setTextColor(mColorPrimary);

            textused.setText(a);
            textused.setGravity(Gravity.START | Gravity.CENTER);
            textused.setPadding(15,0,0,0);

            textfree.setText(b);
            textfree.setGravity(Gravity.END | Gravity.CENTER);
            textfree.setVisibility(View.VISIBLE);
            textfree.setPadding(0,0,15,0);

            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) kotak.getLayoutParams();
            rl.height = 35;
            rl.width = rl.MATCH_PARENT;
            rl.topMargin = 0;
            rl.removeRule(RelativeLayout.BELOW);

            kotak.setLayoutParams(rl);
            kotak.setMax(nabilganteng(total / 1048576.0));
            kotak.setProgress(nabilganteng(usage / 1048576.0));

            bunder.setVisibility(View.GONE);

            kotak.setVisibility(View.VISIBLE);
        } else if (Utilities.styleRamInfo(getContext()) == 2) {
            setVisibility(View.VISIBLE);

            ramuseger.setTextColor(mColorTextPrimary);
            textused.setTextColor(mColorTextPrimary);
            textfree.setTextColor(mColorTextPrimary);

            textused.setText(a);
            textused.setGravity(Gravity.END);

            textfree.setText(b);
            textfree.setGravity(Gravity.START);
            textfree.setVisibility(View.VISIBLE);

            bunder.setMax(nabilganteng(total / 1048576.0));
            bunder.setProgress(nabilganteng(usage / 1048576.0));
            bunder.setVisibility(View.VISIBLE);

            kotak.setVisibility(View.GONE);
        } else if (Utilities.styleRamInfo(getContext()) == 3) {  
            setVisibility(View.VISIBLE);

            ramuseger.setTextColor(mColorTextPrimary);
            textused.setTextColor(mColorTextPrimary);
            textfree.setTextColor(mColorTextPrimary);

            textused.setText(a + " / " + b);
            textused.setGravity(Gravity.CENTER);

            textfree.setVisibility(View.GONE);

            bunder.setVisibility(View.GONE);          
            kotak.setVisibility(View.GONE);
        } else if (Utilities.styleRamInfo(getContext()) == 4) {
            setVisibility(View.VISIBLE);

            ramuseger.setTextColor(mColorTextPrimary);
            textused.setTextColor(mColorTextPrimary);
            textfree.setTextColor(mColorTextPrimary);

            textused.setText(a);
            textused.setGravity(Gravity.START);

            textfree.setText(b);
            textfree.setGravity(Gravity.END);
            textfree.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) kotak.getLayoutParams();
            rl.height = 10;
            rl.width = rl.MATCH_PARENT;
            rl.topMargin = 5;
            rl.addRule(RelativeLayout.BELOW, R.id.nabil_aba_ramview_ll);

            kotak.setLayoutParams(rl);
            kotak.setMax(nabilganteng(total / 1048576.0));
            kotak.setProgress(nabilganteng(usage / 1048576.0));

            bunder.setVisibility(View.GONE);

            kotak.setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    public String nabil(long totalMemory) {
        double kb = totalMemory / 1024.0;
        double mb = totalMemory / 1048576.0;
        double gb = totalMemory / 1073741824.0;
        double tb = totalMemory / 1099511627776.0;

        DecimalFormat twoDecimalForm = new DecimalFormat("#");
        if (tb > 1) {
            twoDecimalForm = new DecimalFormat("#.#");
            finalValue = twoDecimalForm.format(tb).concat(" TB");
        } else if (gb > 1) {
            twoDecimalForm = new DecimalFormat("#.#");
            finalValue = twoDecimalForm.format(gb).concat(" GB");
        } else if (mb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" MB");
        } else if (kb > 1) {
            finalValue = twoDecimalForm.format(mb).concat(" KB");
        } else {
            finalValue = twoDecimalForm.format(totalMemory).concat(" Bytes");
        }

        return finalValue;
    }

    public int nabilganteng(double formate) {       
        DecimalFormat twoDecimalFormPB = new DecimalFormat("#");
        return Integer.valueOf(twoDecimalFormPB.format(formate));
    }

    public class RAMobserver extends ContentObserver{
        public RAMobserver(Handler h) {
            super(h);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            onFinishInflate();
        }

        public void NabilXSasika() {
            //getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("STYLE_RAM_RECENT"), false, this);
            //getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("SHOWHIDE_RAM_RECENT"), false, this);
        }
    }
}
